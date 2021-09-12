package com.ocbc.booking.util;

/**
 * Class for all the SQL queries used.
 * @author darshan
 */
public class Queries {
    public static final String GET_USER_BY_EMAIl = "SELECT u FROM User u where u.email =:email";
}
