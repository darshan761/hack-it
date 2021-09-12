package com.ocbc.booking.exception;

/**
 * Exception for converting object to JSON
 * @author darshan
 */
public class JSONParseException extends Exception{
    private String message;
    public JSONParseException(String message) {
        super(message);
        this.message = message;
    }
    public JSONParseException() {
    }
}
