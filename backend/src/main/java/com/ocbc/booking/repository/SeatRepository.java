package com.ocbc.booking.repository;

import com.ocbc.booking.model.Seat;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * For handling database operations for Seat entity.
 * @author darshan
 */
@Repository
public interface SeatRepository extends CrudRepository<Seat,Integer> {
}
