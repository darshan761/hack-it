package com.ocbc.booking.controller;

import com.ocbc.booking.enums.SeatStatus;
import com.ocbc.booking.model.Seat;
import com.ocbc.booking.service.impl.SeatServiceImpl;
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

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class SeatControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
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
    public void whenRequestAllSeats_thenGetAllSeats() throws Exception {
        when(seatService.getAllSeats()).thenReturn(seats);
        mockMvc.perform(get("/api/v1/seats")).andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    public void whenSeatId_thenGetSeat() throws Exception {
        when(seatService.getSeatById(1)).thenReturn(seat1);
        mockMvc.perform(get("/api/v1/seats/1")).andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.seatId", is(seat1.getSeatId())))
                .andExpect(jsonPath("$.number", is(seat1.getNumber())))
                .andExpect(jsonPath("$.status", is(seat1.getStatus())));
    }

    @Test
    public void whenGivenSeat_thenSaveSeat() throws Exception {
        doNothing().when(seatService).saveSeat(seat1);
        mockMvc.perform(post("/api/v1/seats/")
                        .content(Utils.getJsonString(seat1))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void whenSeatId_thenDeleteSeat() throws Exception {
        doNothing().when(seatService).deleteSeatById(1);
        mockMvc.perform(delete("/api/v1/seats/1/")).andDo(print())
                .andExpect(status().isOk());
    }
}
