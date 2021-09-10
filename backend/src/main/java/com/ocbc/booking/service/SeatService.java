package com.ocbc.booking.service;

import com.ocbc.booking.exception.SeatNotFoundException;
import com.ocbc.booking.model.Seat;

import java.util.List;

public interface SeatService {
    List<Seat> getAllSeats();
    Seat getSeatById(int id) throws SeatNotFoundException;
    void saveSeat(Seat seat);
    void deleteSeatById(int id);
}
