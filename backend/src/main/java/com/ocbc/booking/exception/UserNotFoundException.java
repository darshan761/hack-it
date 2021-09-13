package com.ocbc.booking.exception;

/**
 * Exception if the requested user is not present
 * @author darshan
 */
public class UserNotFoundException extends RuntimeException{
    private final String message;
    public UserNotFoundException(String message) {
        super(message);
        this.message = message;
    }
    @Override
    public String getMessage() {
        return message;
    }
}
