package com.ocbc.booking.exception;

/**
 * Exception for converting object to JSON
 * @author darshan
 */
public class JSONParseException extends Exception{
    private final String message;
    public JSONParseException(String message) {
        super(message);
        this.message = message;
    }
    @Override
    public String getMessage() {
        return message;
    }
}
