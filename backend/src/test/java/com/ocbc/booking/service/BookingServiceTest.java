package com.ocbc.booking.service;

import com.ocbc.booking.dto.BookingDTO;
import com.ocbc.booking.enums.SeatStatus;
import com.ocbc.booking.exception.SeatAlreadyBookedException;
import com.ocbc.booking.model.Mail;
import com.ocbc.booking.model.Seat;
import com.ocbc.booking.model.User;
import com.ocbc.booking.repository.SeatRepository;
import com.ocbc.booking.repository.UserRepository;
import com.ocbc.booking.service.impl.BookingServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

import org.mockito.Spy;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootTest
public class BookingServiceTest {

    @Mock
    private SeatRepository seatRepository;

    @Mock
    private UserRepository userRepository;

    @Spy
    @InjectMocks
    private BookingServiceImpl bookingService;

    @Mock
    private MailService mailService;

    BookingDTO bookingDTO = new BookingDTO();
    User user = new User(1, "darshanpatil761@gmail.com", "Darshan", "123456789");
    List<Seat> seats = new ArrayList<>();
    Seat seat1 = new Seat(1, 'A', 2, SeatStatus.AVAILABLE.toString(), 32.0);
    Seat seat2 = new Seat(2, 'B', 4, SeatStatus.AVAILABLE.toString(), 32.0);
    Seat previousSelectedSeatByExistingUser = new Seat(3, 'C', 2, SeatStatus.BOOKED.toString(), 32.0);

    @BeforeEach
    public void init() {
        user.setSeats(Arrays.asList(previousSelectedSeatByExistingUser));
        bookingDTO.setUser(user);
        seats.add(seat1);
        seats.add(seat2);
        bookingDTO.setSeats(seats);
    }

    @Test
    void whenSelectedSeatsForExistingUser_thenBookSeats() {

        Mail mail = new Mail("darshanpatil761@gmail.com", "Booking Confirmation", "Ticket Booked");

        when(userRepository.findUserByEmail("darshanpatil761@gmail.com")).thenReturn(user);
        when(seatRepository.findById(1)).thenReturn(java.util.Optional.ofNullable(seat1));
        when(seatRepository.findById(2)).thenReturn(java.util.Optional.ofNullable(seat2));
        doNothing().when(mailService).sendMail(mail);
        bookingService.bookSeats(bookingDTO);
        verify(bookingService, times(1)).bookSeats(bookingDTO);
    }

    @Test
    void whenSelectedSeatsForNewUser_thenBookSeats() {

        Mail mail = new Mail("darshanpatil761@gmail.com", "Booking Confirmation", "Ticket Booked");

        when(userRepository.findUserByEmail("darshanpatil761@gmail.com")).thenReturn(null);
        when(seatRepository.findById(1)).thenReturn(java.util.Optional.ofNullable(seat1));
        when(seatRepository.findById(2)).thenReturn(java.util.Optional.ofNullable(seat2));
        when(userRepository.save(user)).thenReturn(user);
        doNothing().when(mailService).sendMail(mail);
        bookingService.bookSeats(bookingDTO);
        verify(bookingService, times(1)).bookSeats(bookingDTO);
    }

    @Test
    void whenSelectedSeatsForAlreadyBookedSeat_thenThrowException() {
        bookingDTO.getSeats().clear();
        bookingDTO.getSeats().add(previousSelectedSeatByExistingUser);
        when(seatRepository.findById(3)).thenReturn(java.util.Optional.ofNullable(previousSelectedSeatByExistingUser));
        Exception exception = assertThrows(SeatAlreadyBookedException.class, () -> {
            bookingService.bookSeats(bookingDTO);
        });

        String expectedMessage = "Seat already got booked by other user!";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.equalsIgnoreCase(expectedMessage));
        verify(bookingService, times(1)).bookSeats(bookingDTO);
    }

    @Test
    void whenRemoveSeatsForAllUsers_thenDeleteBooking() {
        bookingService.deleteBookingForAllUsers();
        verify(bookingService, times(1)).deleteBookingForAllUsers();
    }
}
