package com.ocbc.booking.exception;

/**
 * Exception if the requested seat is not present
 * @author darshan
 */
public class SeatNotFoundException extends RuntimeException{
    private String message;
    public SeatNotFoundException(String message) {
        super(message);
        this.message = message;
    }
    public SeatNotFoundException() {
    }
}
