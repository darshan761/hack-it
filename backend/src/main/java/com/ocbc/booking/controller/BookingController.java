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

/**
 * Controller for handling booking requests
 * @author darshan
 */
@RestController
@RequestMapping(path = "/api/v1/book")
@Api(tags = {SwaggerConfig.BOOKING_TAG})
public class BookingController {

    private static final Logger logger = LoggerFactory.getLogger(BookingController.class);

    @Autowired
    BookingService bookingService;

    /**
     * For booking seats for the user
     * @param bookingDTO - user and seats details from UI
     */
    @CrossOrigin
    @ApiOperation("Book seats for users")
    @PostMapping("")
    public void bookSeatForUser(@RequestBody BookingDTO bookingDTO) {
        logger.info("/book  - book seat for user request received");
        bookingService.bookSeats(bookingDTO);
    }

    /**
     * For removing all the bookings of all the users
     */
    @ApiOperation("To reset/delete bookings of all users")
    @PutMapping("/reset")
    public void deleteBookingForAllUsers() {
        logger.info("/book/reset/ - delete booking for all users request received");
        bookingService.deleteBookingForAllUsers();
    }
}
