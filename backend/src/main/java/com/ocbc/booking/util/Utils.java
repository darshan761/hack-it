package com.ocbc.booking.util;

import com.ocbc.booking.dto.BookingDTO;

public class Utils {
    public static String createEmailMessage(BookingDTO bookingDTO){
        String name = bookingDTO.getUser().getName();
        int seats = bookingDTO.getSeats().size();
        Double totalPrice = bookingDTO.getSeats().stream().mapToDouble(seat -> seat.getPrice()).sum();
        return new StringBuilder().append("Hi "+name+",\n")
                .append("Your booking for "+seats+ " seats at SGD "+ totalPrice+ " is confirmed.\n")
                .append("Enjoy your Movie!\n").toString();
    }
}
