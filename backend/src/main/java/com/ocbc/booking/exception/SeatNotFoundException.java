package com.ocbc.booking.exception;

/**
 * Exception if the requested seat is not present
 *
 * @author darshan
 */
public class SeatNotFoundException extends RuntimeException {
    private final String message;

    public SeatNotFoundException(String message) {
        super(message);
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
