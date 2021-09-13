package com.ocbc.booking.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * Controller for handling exceptions
 * @author darshan
 */
@ControllerAdvice
public class ControllerResponseExceptionHandler extends ResponseEntityExceptionHandler {

    private static final Logger logging = LoggerFactory.getLogger(ControllerResponseExceptionHandler.class);

    /**
     * For handling already booked seat exception
     * @param seatAlreadyBookedException exception
     * @return Conflict response entity with user-friendly message for seatAlreadyBookedException
     */
    @ExceptionHandler(value
            = { SeatAlreadyBookedException.class })
    protected ResponseEntity<Object> handleBookingConflict(
            SeatAlreadyBookedException seatAlreadyBookedException) {
        logging.info("Handling Booking conflict");
        return new ResponseEntity<>(seatAlreadyBookedException.getMessage(), HttpStatus.CONFLICT);
    }

    /**
     * For handling exception for non-existing user
     * @param userNotFoundException exception
     * @return Conflict response entity with user-friendly message for userNotFoundException
     */
    @ExceptionHandler(value
            = { UserNotFoundException.class })
    protected ResponseEntity<Object> handleUserConflict(
            UserNotFoundException userNotFoundException) {
        logging.info("Handling User conflict");
        return new ResponseEntity<>(userNotFoundException.getMessage(), HttpStatus.CONFLICT);
    }

    /**
     * For handling exception for non-existing seat
     * @param seatNotFoundException exception
     * @return Conflict response entity with user-friendly message for seatNotFoundException
     */
    @ExceptionHandler(value
            = { SeatNotFoundException.class })
    protected ResponseEntity<Object> handleSeatConflict(
            SeatNotFoundException seatNotFoundException) {
        logging.info("Handling Seat conflict");
        return new ResponseEntity<>(seatNotFoundException.getMessage(), HttpStatus.CONFLICT);
    }

}

