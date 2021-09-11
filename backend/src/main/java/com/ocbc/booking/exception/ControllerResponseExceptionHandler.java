package com.ocbc.booking.exception;

import com.ocbc.booking.controller.BookingController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ControllerResponseExceptionHandler extends ResponseEntityExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(ControllerResponseExceptionHandler.class);

    @ExceptionHandler(value
            = { SeatAlreadyBookedException.class })
    protected ResponseEntity<Object> handleBookingConflict(
            SeatAlreadyBookedException seatAlreadyBookedException) {
        logger.info("Handling Booking conflict");
        return new ResponseEntity(seatAlreadyBookedException.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(value
            = { UserNotFoundException.class })
    protected ResponseEntity<Object> handleUserConflict(
            UserNotFoundException userNotFoundException) {
        logger.info("Handling User conflict");
        return new ResponseEntity(userNotFoundException.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(value
            = { SeatNotFoundException.class })
    protected ResponseEntity<Object> handleSeatConflict(
            SeatNotFoundException seatNotFoundException) {
        logger.info("Handling Seat conflict");
        return new ResponseEntity(seatNotFoundException.getMessage(), HttpStatus.CONFLICT);
    }

}

