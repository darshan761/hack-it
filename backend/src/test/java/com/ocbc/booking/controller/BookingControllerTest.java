package com.ocbc.booking.controller;

import com.ocbc.booking.dto.BookingDTO;
import com.ocbc.booking.enums.SeatStatus;
import com.ocbc.booking.model.Seat;
import com.ocbc.booking.model.User;
import com.ocbc.booking.service.impl.BookingServiceImpl;
import com.ocbc.booking.util.Utils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Test for booking Controller class
 * @author darshan
 */
@SpringBootTest
@AutoConfigureMockMvc
public class BookingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookingServiceImpl bookingService;

    BookingDTO bookingDTO = new BookingDTO();
    User user = new User(1, "darshanpatil761@gmail.com", "Darshan", "123456789");
    List<Seat> seats = new ArrayList<>();
    Seat seat1 = new Seat(1, 'A', 2, SeatStatus.AVAILABLE.toString(), 32.0);
    Seat seat2 = new Seat(2, 'B', 4, SeatStatus.BOOKED.toString(), 32.0);

    @BeforeEach
    public void init() {
        bookingDTO.setUser(user);
        seats.add(seat1);
        seats.add(seat2);
        bookingDTO.setSeats(seats);
    }

    @Test
    public void whenSeatsSelectedByUser_thenBookSeat() throws Exception {
        doNothing().when(bookingService).bookSeats(bookingDTO);
        mockMvc.perform(post("/api/v1/book/")
                        .content(Utils.getJsonString(bookingDTO))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void whenRemoveSeatsForAllUsers_thenDeleteBooking() throws Exception {
        doNothing().when(bookingService).deleteBookingForAllUsers();
        mockMvc.perform(put("/api/v1/book/reset/")).andDo(print())
                .andExpect(status().isOk());
    }
}
