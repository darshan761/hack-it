package com.ocbc.booking.service.impl;

import com.ocbc.booking.model.Seat;
import com.ocbc.booking.model.User;
import com.ocbc.booking.repository.SeatRepository;
import com.ocbc.booking.repository.UserRepository;
import com.ocbc.booking.service.SeatService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SeatServiceImpl implements SeatService {
    private static final Logger logger = LoggerFactory.getLogger(SeatServiceImpl.class);

    @Autowired
    SeatRepository seatRepository;

    @Override
    public List<Seat> getAllSeats() {
        List<Seat> seats = new ArrayList<>();
        logger.info("Getting seats from the database");
        seatRepository.findAll().forEach(seats::add);
        return seats;
    }

    @Override
    public Seat getSeatById(int id) {
        logger.info("Getting seat by id {} from the database", id);
        return seatRepository.findById(id).get();
    }

    @Override
    public void saveSeat(Seat seat) {
        logger.info("Saving {} from the database", seat);
        seatRepository.save(seat);
    }

    @Override
    public void deleteSeatById(int id) {
        logger.info("Deleting seat id {} from the database", id);
        seatRepository.deleteById(id);
    }
}
