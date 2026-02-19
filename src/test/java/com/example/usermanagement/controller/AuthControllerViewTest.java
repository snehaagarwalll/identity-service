package com.example.usermanagement.controller;

import com.example.usermanagement.dto.AuthResponse;
import com.example.usermanagement.dto.LoginRequest;
import com.example.usermanagement.dto.RegisterRequest;
import com.example.usermanagement.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthControllerViewTest {

    private UserService userService;
    private AuthController authController;

    @BeforeEach
    void setUp() {
        userService = Mockito.mock(UserService.class);
        authController = new AuthController(userService);
    }

    @Test
    void testRegisterEndpoint() {

        RegisterRequest request = new RegisterRequest();
        request.setName("Sneha");
        request.setEmail("sneha@gmail.com");
        request.setPassword("1234");
        request.setRole("CUSTOMER");

        doNothing().when(userService).register(any());

        ResponseEntity<?> response = authController.register(request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testLoginEndpoint() {

        LoginRequest request = new LoginRequest();
        request.setEmail("sneha@gmail.com");
        request.setPassword("1234");

        when(userService.login(any()))
                .thenReturn(new AuthResponse("dummy-token"));

        ResponseEntity<?> response = authController.login(request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}
