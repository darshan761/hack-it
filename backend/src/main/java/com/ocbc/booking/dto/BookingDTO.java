package com.ocbc.booking.dto;

import com.ocbc.booking.model.Seat;
import com.ocbc.booking.model.User;

import java.util.List;

public class BookingDTO {
    private User user;
    private List<Seat> seats;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Seat> getSeats() {
        return seats;
    }

    public void setSeats(List<Seat> seats) {
        this.seats = seats;
    }

    @Override
    public String toString() {
        return "BookingDTO{" +
                "user=" + user +
                ", seats=" + seats +
                '}';
    }
}
