package com.example.usermanagement.service;

import com.example.usermanagement.dto.AuthResponse;
import com.example.usermanagement.dto.LoginRequest;
import com.example.usermanagement.dto.RegisterRequest;
import com.example.usermanagement.exception.InvalidCredentialsException;
import com.example.usermanagement.exception.UserAlreadyExistsException;
import com.example.usermanagement.model.Role;
import com.example.usermanagement.model.User;
import com.example.usermanagement.repository.UserRepository;
import com.example.usermanagement.security.JwtUtil;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @Mock
    private JwtUtil jwtUtil;

    @InjectMocks
    private UserService userService;

    private RegisterRequest registerRequest;
    private LoginRequest loginRequest;
    private User user;

    @BeforeEach
    void setUp() {

        registerRequest = new RegisterRequest();
        registerRequest.setName("Sneha");
        registerRequest.setEmail("sneha@gmail.com");
        registerRequest.setPassword("1234");
        registerRequest.setRole("CUSTOMER");

        loginRequest = new LoginRequest();
        loginRequest.setEmail("sneha@gmail.com");
        loginRequest.setPassword("1234");

        user = User.builder()
                .id(1L)
                .name("Sneha")
                .email("sneha@gmail.com")
                .password("encoded-password")
                .role(Role.CUSTOMER)
                .build();
    }

    @Test
    void testRegisterSuccess() {

        when(userRepository.findByEmail(registerRequest.getEmail()))
                .thenReturn(Optional.empty());

        when(passwordEncoder.encode(registerRequest.getPassword()))
                .thenReturn("encoded-password");

        userService.register(registerRequest);

        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void testRegisterEmailAlreadyExists() {

        when(userRepository.findByEmail(registerRequest.getEmail()))
                .thenReturn(Optional.of(user));

        assertThrows(UserAlreadyExistsException.class,
                () -> userService.register(registerRequest));

        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void testLoginSuccess() {

        when(userRepository.findByEmail(loginRequest.getEmail()))
                .thenReturn(Optional.of(user));

        when(passwordEncoder.matches(loginRequest.getPassword(), user.getPassword()))
                .thenReturn(true);

        when(jwtUtil.generateToken(user.getEmail(), user.getRole().name()))
                .thenReturn("dummy-jwt-token");

        AuthResponse response = userService.login(loginRequest);

        assertNotNull(response);
        assertEquals("dummy-jwt-token", response.getToken());

        verify(jwtUtil, times(1))
                .generateToken(user.getEmail(), user.getRole().name());
    }

    @Test
    void testLoginInvalidEmail() {

        when(userRepository.findByEmail(loginRequest.getEmail()))
                .thenReturn(Optional.empty());

        assertThrows(InvalidCredentialsException.class,
                () -> userService.login(loginRequest));
    }

    @Test
    void testLoginWrongPassword() {

        when(userRepository.findByEmail(loginRequest.getEmail()))
                .thenReturn(Optional.of(user));

        when(passwordEncoder.matches(loginRequest.getPassword(), user.getPassword()))
                .thenReturn(false);

        assertThrows(InvalidCredentialsException.class,
                () -> userService.login(loginRequest));
    }
}
