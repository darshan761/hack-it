package com.ocbc.booking.exception;

public class SeatAlreadyBookedException extends RuntimeException{
    private String message;
    public SeatAlreadyBookedException(String message) {
        super(message);
        this.message = message;
    }
    public SeatAlreadyBookedException() {
    }
}
