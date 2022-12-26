package com.bonqa.bonqa.unit_tests;

import com.bonqa.bonqa.controller.UserController;
import com.bonqa.bonqa.model.User;
import com.bonqa.bonqa.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class UserControllerTests {

    @Mock private UserRepository userRepository;
    private UserController userController;

    @BeforeEach
    public void setup(){
        userController = new UserController(userRepository);
    }

    @Test
    public void testFetchByID_whenUserFound_shouldReturn200() {
        User user = new User();

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        ResponseEntity<User> response = userController.fetchByID(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(user, response.getBody());
    }

    @Test
    void testFetchByID_whenUserNotFound_shouldReturn404() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        ResponseEntity<User> response = userController.fetchByID(1L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }


    @Test
    void testFetchByUserName_whenUserFound_ReturnsOk(){
        String username = "test";
        User user = new User();
        user.setUsername(username);
        when(userRepository.findOneByUsername(username)).thenReturn(Optional.of(user));

        ResponseEntity<User> response = userController.fetchByUsername(username);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(user, response.getBody());
    }
    @Test
    void testFetchByUsername_whenUserNotFound_returnsNotFound() {
        String username = "test";
        when(userRepository.findOneByUsername(username)).thenReturn(Optional.empty());

        ResponseEntity<User> response = userController.fetchByUsername(username);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testFetchByUsername_whenExceptionThrown_returnsInternalServerError() {
        String username = "test";
        when(userRepository.findOneByUsername(username)).thenThrow(new RuntimeException());

        ResponseEntity<User> response = userController.fetchByUsername(username);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void testFetchByID_whenExceptionThrown_shouldReturn500() {
        // Set up mock repository to throw an exception when findById() is called
        when(userRepository.findById(1L)).thenThrow(new RuntimeException());

        // Invoke the method under test
        ResponseEntity<User> response = userController.fetchByID(1L);

        // Verify that the method returns a 500 INTERNAL SERVER ERROR response
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }



}
