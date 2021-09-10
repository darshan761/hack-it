package com.ocbc.booking.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ControllerResponseExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value
            = { IllegalArgumentException.class, IllegalStateException.class })
    protected ResponseEntity<Object> handleConflict(
            RuntimeException ex, WebRequest request) {
        String bodyOfResponse = "This should be application specific";
        return handleExceptionInternal(ex, bodyOfResponse,
                new HttpHeaders(), HttpStatus.CONFLICT, request);
    }

    @ExceptionHandler(value
            = { SeatAlreadyBookedException.class })
    protected ResponseEntity<Object> handleBookingConflict(
            SeatAlreadyBookedException seatAlreadyBookedException) {
        return new ResponseEntity(seatAlreadyBookedException.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(value
            = { UserNotFoundException.class })
    protected ResponseEntity<Object> handleUserConflict(
            UserNotFoundException userNotFoundException) {
        return new ResponseEntity(userNotFoundException.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(value
            = { SeatNotFoundException.class })
    protected ResponseEntity<Object> handleSeatConflict(
            SeatNotFoundException seatNotFoundException) {
        return new ResponseEntity(seatNotFoundException.getMessage(), HttpStatus.CONFLICT);
    }

}

