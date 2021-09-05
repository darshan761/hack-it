package com.ocbc.booking.repository;

import com.ocbc.booking.model.Seat;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SeatRepository extends CrudRepository<Seat,Integer> {
}
