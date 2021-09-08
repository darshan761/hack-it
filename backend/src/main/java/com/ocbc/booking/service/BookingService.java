package com.ocbc.booking.service;

import com.ocbc.booking.dto.BookingDTO;
import com.ocbc.booking.model.Seat;
import com.ocbc.booking.model.User;

import java.util.List;

public interface BookingService {
    void bookSeats(BookingDTO bookingDTO);

    List<Seat> getAllSeats();

    List<User> getAllUsers();

    void deleteBookingForAllUsers();
}
