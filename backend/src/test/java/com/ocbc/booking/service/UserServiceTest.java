package com.ocbc.booking.service;

import com.ocbc.booking.exception.UserNotFoundException;
import com.ocbc.booking.model.User;
import com.ocbc.booking.repository.UserRepository;
import com.ocbc.booking.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Test for user CRUD operations
 * @author darshan
 */
@SpringBootTest
public class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    @Spy
    @InjectMocks
    private UserServiceImpl userService;

    List<User> users = new ArrayList<>();
    User user1 = new User(1, "darshanpatil761@gmail.com", "Darshan", "123456789");
    User user2 = new User(2, "john.doe@gmail.com", "Joe", "21324536");

    @BeforeEach
    public void init() {
        users.add(user1);
        users.add(user2);
    }

    @Test
    void whenRequestUsers_thenGetAllUsers() {
        when(userRepository.findAll()).thenReturn(users);
        List<User> exUsers = userService.getAllUsers();
        assertThat(exUsers.size())
                .isEqualTo(users.size());
    }

    @Test
    void whenUserId_thenGetUser() {
        when(userRepository.findById(1)).thenReturn(java.util.Optional.ofNullable(user1));
        User exUser = userService.getUserById(1);
        assertThat(exUser.getEmail())
                .isEqualTo(user1.getEmail());
    }

    @Test
    void whenUserNotFound_thenThrowException() {
        Exception exception = assertThrows(UserNotFoundException.class, () -> {
            userService.getUserById(3);
        });
        String expectedMessage = "No user with id 3 found";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.equalsIgnoreCase(expectedMessage));
        verify(userService, times(1)).getUserById(3);
    }

    @Test
    void whenUser_thenSaveUser() {
        doNothing().when(userService).saveUser(user1);
        userService.saveUser(user1);
        verify(userService, times(1)).saveUser(user1);
    }

    @Test
    void whenUserId_thenDeleteUser() {
        doNothing().when(userRepository).deleteById(1);
        userService.deleteUserById(1);
        verify(userService, times(1)).deleteUserById(1);
    }
}
