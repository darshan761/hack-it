package com.ocbc.booking.service;

import com.ocbc.booking.model.Seat;
import com.ocbc.booking.repository.SeatRepository;
import com.ocbc.booking.service.impl.BookingServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;

import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class BookingServiceTest {

    @Mock
    private SeatRepository seatRepository;

    @InjectMocks
    private BookingServiceImpl bookingService;

    @BeforeEach
    public void init() {
    }

//    @Test
//    void getAllSeats() {
//
//        List<Seat> seats = new ArrayList<>();
//        seats.add(new Seat());
//
//        when(seatRepository.findAll()).thenReturn(seats);
//
//        List<Seat> exSeats = bookingService.getAllSeats();
//
//        assertThat(exSeats.size())
//                .isEqualTo(seats.size());
//    }
}
