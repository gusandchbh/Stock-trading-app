package com.bonqa.bonqa.unit_tests;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import com.bonqa.bonqa.controller.UserController;
import com.bonqa.bonqa.domain.model.data.request.LoginRequest;
import com.bonqa.bonqa.domain.model.data.request.RegisterRequest;
import com.bonqa.bonqa.exception.InvalidCredentialsException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bonqa.bonqa.domain.model.User;
import com.bonqa.bonqa.domain.repository.UserRepository;
import com.bonqa.bonqa.domain.user.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private User user1;
    private User user2;

    @BeforeEach
    public void setUp() {
        user1 = new User();
        user1.setUsername("John");
        user1.setPassword("Password");
        user1.setEmail("email@test.com");
        user2 = new User();
        user2.setUsername("Test2");
    }

    @Test
    @DisplayName("Test all() method")
    void testAll() {
        List<User> users = new ArrayList<>();
        users.add(user1);
        users.add(user2);
        when(userRepository.findAll()).thenReturn(users);
        Iterable<User> result = userController.all();
        Assertions.assertIterableEquals(users, result);
    }

    @Test
    @DisplayName("Test successful login")
    void testLogin() {
        // Arrange
        LoginRequest loginRequest = new LoginRequest("username", "password");
        String token = "dummy-token";
        when(userService.loginUser(loginRequest)).thenReturn(token);

        ResponseEntity<String> response = userController.login(loginRequest);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(token, response.getBody());
    }

    @Test
    @DisplayName("Test unsuccessful login with invalid credentials")
    void testUnsuccessfulLogin() {
        // Arrange
        LoginRequest loginRequest = new LoginRequest("invalid-username", "invalid-password");
        when(userService.loginUser(loginRequest)).thenThrow(new InvalidCredentialsException("Invalid username or password"));

        ResponseEntity<String> response = userController.login(loginRequest);

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertEquals("Invalid username or password", response.getBody());
    }

    @Test
    @DisplayName("Test successful user registration")
    void testSuccessfulRegistration() {
        RegisterRequest registerRequest = new RegisterRequest("John", "Password", "email@test.com");
        when(userService.registerUser(registerRequest)).thenReturn(user1);

        ResponseEntity<User> response = userController.registerUser(registerRequest);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(user1, response.getBody());
    }









}
