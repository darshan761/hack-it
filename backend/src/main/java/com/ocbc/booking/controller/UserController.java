package com.ocbc.booking.controller;


import com.ocbc.booking.config.SwaggerConfig;
import com.ocbc.booking.model.Seat;
import com.ocbc.booking.model.User;
import com.ocbc.booking.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller for handling user CRUD request
 * @author darshan
 */
@RestController
@RequestMapping(path = "/api/v1/users")
@Api(tags = {SwaggerConfig.USER_TAG})
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    UserService userService;

    @ApiOperation("Get all the users")
    @GetMapping("")
    public List<User> getAllUsers() {
        logger.info("/users - get all users request received");
        return userService.getAllUsers();
    }

    @ApiOperation("Get user by userId")
    @GetMapping("/{id}")
    public User getUserById(@PathVariable int id) {
        logger.info("/users/{} - get user by id request received",id);
        return userService.getUserById(id);
    }

    @ApiOperation("Save user")
    @PostMapping("")
    public void saveSeat(@RequestBody User user) {
        logger.info("/users - save user request received");
        userService.saveUser(user);
    }

    @ApiOperation("Delete user by Id")
    @DeleteMapping("/{id}")
    public void deleteUserById(@PathVariable int id) {
        logger.info("/users - delete user request received");
        userService.deleteUserById(id);
    }
}
