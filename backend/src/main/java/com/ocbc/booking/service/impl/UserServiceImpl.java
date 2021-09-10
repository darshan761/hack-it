package com.ocbc.booking.service.impl;

import com.ocbc.booking.exception.UserNotFoundException;
import com.ocbc.booking.model.Seat;
import com.ocbc.booking.model.User;
import com.ocbc.booking.repository.UserRepository;
import com.ocbc.booking.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    UserRepository userRepository;

    @Override
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        logger.info("Getting users from the database");
        userRepository.findAll().forEach(users::add);
        return users;
    }

    @Override
    public User getUserById(int id) throws UserNotFoundException {
        logger.info("Getting user by id {} from the database", id);
        if(!userRepository.findById(id).isPresent()){
            logger.error("No user with id {} found", id);
            throw new UserNotFoundException("No user with id "+ id + " found");
        }
        else{
            return userRepository.findById(id).get();
        }
    }

    @Override
    public void saveUser(User user) {
        logger.info("Saving {} from the database", user);
        userRepository.save(user);
    }

    @Override
    public void deleteUserById(int id) {
        logger.info("Deleting user id {} from the database", id);
        userRepository.deleteById(id);
    }
}
