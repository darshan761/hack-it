package com.ocbc.booking.controller;

import com.ocbc.booking.config.SwaggerConfig;
import com.ocbc.booking.dto.BookingDTO;
import com.ocbc.booking.model.Seat;
import com.ocbc.booking.model.User;
import com.ocbc.booking.service.BookingService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/v1/book")
@Api(tags = {SwaggerConfig.BOOKING_TAG})
public class BookingController {

    private static final Logger logger = LoggerFactory.getLogger(BookingController.class);

    @Autowired
    BookingService bookingService;

    @CrossOrigin
    @ApiOperation("Book seats for users")
    @PostMapping("")
    public void bookSeatForUser(@RequestBody BookingDTO bookingDTO) {
        logger.info("/book  - bookSeatForUser request received");
        bookingService.bookSeats(bookingDTO);
    }

    @ApiOperation("To reset/delete bookings of all users")
    @PutMapping("/reset")
    public void deleteBookingForAllUsers() {
        logger.info("/book/reset/ - deleteBookingForAllUsers request received");
        bookingService.deleteBookingForAllUsers();
    }
}
