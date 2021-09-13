package com.ocbc.booking.exception;

/**
 * Exception if the selected seat is got booked by other user
 * @author darshan
 */
public class SeatAlreadyBookedException extends RuntimeException{
    private final String message;
    public SeatAlreadyBookedException(String message) {
        super(message);
        this.message = message;
    }
    @Override
    public String getMessage() {
        return message;
    }
}
