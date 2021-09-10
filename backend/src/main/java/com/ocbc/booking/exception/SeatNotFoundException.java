package com.ocbc.booking.exception;

public class SeatNotFoundException extends RuntimeException{
    private String message;
    public SeatNotFoundException(String message) {
        super(message);
        this.message = message;
    }
    public SeatNotFoundException() {
    }
}
