package com.ocbc.booking.model;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Represents Seat entity
 * @author darshan
 */
@Entity
public class Seat {
    @Id
    private int seatId;
    private char rowName;
    private int number;
    private String status;
    private Double price;

    public Seat() {
    }

    public Seat(int seatId, char rowName, int number, String status, Double price) {
        this.seatId = seatId;
        this.rowName = rowName;
        this.number = number;
        this.status = status;
        this.price = price;
    }

    public int getSeatId() {
        return seatId;
    }

    public void setSeatId(int seatId) {
        this.seatId = seatId;
    }

    public char getRowName() {
        return rowName;
    }

    public void setRowName(char rowName) {
        this.rowName = rowName;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Seat{" +
                "seatId=" + seatId +
                ", rowName=" + rowName +
                ", number=" + number +
                ", status='" + status + '\'' +
                ", price=" + price +
                '}';
    }
}
