package com.ocbc.booking.service;

import com.ocbc.booking.dto.BookingDTO;
import com.ocbc.booking.exception.SeatAlreadyBookedException;

public interface BookingService {
    void bookSeats(BookingDTO bookingDTO) throws SeatAlreadyBookedException;
    void deleteBookingForAllUsers();
}
