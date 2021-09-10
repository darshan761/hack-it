package com.ocbc.booking.repository;

import com.ocbc.booking.util.Queries;
import com.ocbc.booking.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User,Integer> {
    @Query(Queries.GET_USER_BY_EMAIl)
    User findUserByEmail(@Param("email") String email);
}
