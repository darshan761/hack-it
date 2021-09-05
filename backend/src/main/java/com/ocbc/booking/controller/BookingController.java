package com.ocbc.booking.controller;

import com.ocbc.booking.dto.BookingDTO;
import com.ocbc.booking.model.Seat;
import com.ocbc.booking.model.User;
import com.ocbc.booking.service.BookingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class BookingController {

    private static final Logger logger = LoggerFactory.getLogger(BookingController.class);

    @Autowired
    BookingService bookingService;

    @GetMapping("/greeting")
    public String greeting() {
        return "Welcome to HACK-IT @ OCBC!";
    }

    @CrossOrigin
    @GetMapping("/seats")
    public List<Seat> getAllSeats(){
        logger.info("/seats - getAllSeats request received");
        return bookingService.getAllSeats();
    }

    @CrossOrigin
    @PostMapping("/book")
    public void bookSeatForUser(@RequestBody BookingDTO bookingDTO){
        logger.info("/book  - bookSeatForUser request received");
        bookingService.bookSeats(bookingDTO);
    }

    @GetMapping("/users")
    public List<User> getAllUsers(){
        logger.info("/users - getAllUsers request received");
        return bookingService.getAllUsers();
    }
}
