package com.ocbc.booking.repository;

import com.ocbc.booking.util.Queries;
import com.ocbc.booking.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * For handling database operations for User entity.
 * @author darshan
 */
@Repository
public interface UserRepository extends CrudRepository<User,Integer> {
    /**
     * Get user from its unique email attribute
     * @param email user's emailID
     * @return user if exists
     */
    @Query(Queries.GET_USER_BY_EMAIl)
    User findUserByEmail(@Param("email") String email);
}
