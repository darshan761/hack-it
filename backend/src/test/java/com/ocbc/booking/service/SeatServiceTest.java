package com.ocbc.booking.service;

import com.ocbc.booking.enums.SeatStatus;
import com.ocbc.booking.exception.SeatNotFoundException;
import com.ocbc.booking.model.Seat;
import com.ocbc.booking.repository.SeatRepository;
import com.ocbc.booking.service.impl.SeatServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class SeatServiceTest {

    @Mock
    private SeatRepository seatRepository;

    @Spy
    @InjectMocks
    private SeatServiceImpl seatService;

    List<Seat> seats = new ArrayList<>();
    Seat seat1 = new Seat(1, 'A', 2, SeatStatus.AVAILABLE.toString(), 32.0);
    Seat seat2 = new Seat(2, 'B', 4, SeatStatus.BOOKED.toString(), 32.0);

    @BeforeEach
    public void init() {
        seats.add(seat1);
        seats.add(seat2);
    }

    @Test
    void whenRequestSeats_thenGetAllSeats() {
        when(seatRepository.findAll()).thenReturn(seats);
        List<Seat> exSeats = seatService.getAllSeats();
        assertThat(exSeats.size())
                .isEqualTo(seats.size());
    }

    @Test
    void whenSeatId_thenGetSeat() {
        when(seatRepository.findById(1)).thenReturn(java.util.Optional.ofNullable(seat1));
        Seat exSeat = seatService.getSeatById(1);
        assertThat(exSeat.getRowName())
                .isEqualTo(seat1.getRowName());
    }

    @Test
    void whenSeatNotFound_thenThrowException() {
        Exception exception = assertThrows(SeatNotFoundException.class, () -> {
            seatService.getSeatById(3);
        });
        String expectedMessage = "No seat with id 3 found";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.equalsIgnoreCase(expectedMessage));
        verify(seatService, times(1)).getSeatById(3);
    }

    @Test
    void whenSeat_thenSaveSeat() {
        doNothing().when(seatRepository).save(seat1);
        seatService.saveSeat(seat1);
        verify(seatService, times(1)).saveSeat(seat1);
    }

    @Test
    void whenSeatId_thenDeleteSeat() {
        doNothing().when(seatRepository).deleteById(1);
        seatService.deleteSeatById(1);
        verify(seatService, times(1)).deleteSeatById(1);
    }
}
