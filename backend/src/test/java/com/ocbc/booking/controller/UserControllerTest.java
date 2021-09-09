package com.ocbc.booking.controller;

import com.ocbc.booking.dto.BookingDTO;
import com.ocbc.booking.model.Seat;
import com.ocbc.booking.model.User;
import com.ocbc.booking.service.impl.BookingServiceImpl;
import com.ocbc.booking.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserServiceImpl userService;

    @BeforeEach
    public void init() {

    }

    @Test
    public void getAllUsers() throws Exception {

        List<User> users = new ArrayList<>();
        users.add(new User());
        when(userService.getAllUsers()).thenReturn(users);

        mockMvc.perform(get("/api/v1/users")).andDo(print())
                .andExpect(status().isOk());

    }
}
