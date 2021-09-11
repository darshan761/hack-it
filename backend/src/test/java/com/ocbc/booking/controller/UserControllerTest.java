package com.ocbc.booking.controller;

import com.ocbc.booking.model.User;
import com.ocbc.booking.service.impl.UserServiceImpl;
import com.ocbc.booking.util.Utils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
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
    public void whenRequestAllUser_thenGetAllUsers() throws Exception {
        when(userService.getAllUsers()).thenReturn(users);
        mockMvc.perform(get("/api/v1/users")).andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    public void whenUserId_thenGetUser() throws Exception {
        when(userService.getUserById(1)).thenReturn(user1);
        mockMvc.perform(get("/api/v1/users/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.userId", is(user1.getUserId())))
                .andExpect(jsonPath("$.name", is(user1.getName())))
                .andExpect(jsonPath("$.email", is(user1.getEmail())));
    }

    @Test
    public void whenGivenUser_thenSaveUser() throws Exception {
        doNothing().when(userService).saveUser(user1);
        mockMvc.perform(post("/api/v1/users/")
                        .content(Utils.getJsonString(user1))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void whenGivenUser_thenDeleteUser() throws Exception {
        doNothing().when(userService).deleteUserById(1);
        mockMvc.perform(delete("/api/v1/users/1/")).andDo(print())
                .andExpect(status().isOk());
    }
}
