package com.ocbc.booking.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ocbc.booking.dto.BookingDTO;
import com.ocbc.booking.exception.JSONParseException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Utils for the BookingApplication.
 * @author darshan
 */
public class Utils {
    public static String createEmailMessage(BookingDTO bookingDTO) {
        String name = bookingDTO.getUser().getName();
        int seats = bookingDTO.getSeats().size();
        List<String> bookedSeatList = bookingDTO.getSeats().stream().map(seat -> seat.getRowName() + String.valueOf(seat.getNumber())).collect(Collectors.toList());
        Double totalPrice = bookingDTO.getSeats().stream().mapToDouble(seat -> seat.getPrice()).sum();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        return new StringBuilder().append("Hi " + name + ",\n")
                .append("Your booking ( " + seats + " seat(s) " + bookedSeatList + ", SGD " + totalPrice + ") is confirmed at " + LocalDateTime.now().format(formatter) + ".\n")
                .append("Enjoy your Movie!\n").toString();
    }

    public static String getJsonString(final Object obj) throws JSONParseException {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new JSONParseException(e.getMessage());
        }
    }
}
