package com.ocbc.booking.service;

import com.ocbc.booking.exception.UserNotFoundException;
import com.ocbc.booking.model.Seat;
import com.ocbc.booking.model.User;

import java.util.List;

public interface UserService {
    List<User> getAllUsers();
    User getUserById(int id) throws UserNotFoundException;
    void saveUser(User user);
    void deleteUserById(int id);
}
