package com.ocbc.booking.service;

import com.ocbc.booking.model.User;
import com.ocbc.booking.repository.SeatRepository;
import com.ocbc.booking.repository.UserRepository;
import com.ocbc.booking.service.impl.BookingServiceImpl;
import com.ocbc.booking.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    public void init() {
    }

    @Test
    void getAllUsers() {

        List<User> users = new ArrayList<>();
        users.add(new User());

        when(userRepository.findAll()).thenReturn(users);

        List<User> exUsers = userService.getAllUsers();

        assertThat(exUsers.size())
                .isEqualTo(users.size());
    }
}
