package com.ocbc.booking.exception;

/**
 * Exception if the selected seat is got booked by other user
 * @author darshan
 */
public class SeatAlreadyBookedException extends RuntimeException{
    private String message;
    public SeatAlreadyBookedException(String message) {
        super(message);
        this.message = message;
    }
    public SeatAlreadyBookedException() {
    }
}
