package com.ocbc.booking.exception;

public class JSONParseException extends Exception{
    private String message;
    public JSONParseException(String message) {
        super(message);
        this.message = message;
    }
    public JSONParseException() {
    }
}