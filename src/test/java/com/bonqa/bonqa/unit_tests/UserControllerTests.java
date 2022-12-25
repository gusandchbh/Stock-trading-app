package com.bonqa.bonqa.unit_tests;

import com.bonqa.bonqa.controller.UserController;
import com.bonqa.bonqa.model.User;
import com.bonqa.bonqa.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.junit.jupiter.api.Assertions;
import org.springframework.test.web.servlet.MockMvc;
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
    void testFetchByUserName_whenUserFound_ReturnsOk(){
        String username = "test";
        User user = new User();
        user.setUsername(username);
        when(userRepository.findOneByUsername(username)).thenReturn(Optional.of(user));

        ResponseEntity<User> response = userController.fetchByUsername(username);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals(user, response.getBody());
    }
    @Test
    void testFetchByUsername_whenUserNotFound_returnsNotFound() {
        String username = "test";
        when(userRepository.findOneByUsername(username)).thenReturn(Optional.empty());

        ResponseEntity<User> response = userController.fetchByUsername(username);

        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testFetchByUsername_whenExceptionThrown_returnsInternalServerError() {
        String username = "test";
        when(userRepository.findOneByUsername(username)).thenThrow(new RuntimeException());

        ResponseEntity<User> response = userController.fetchByUsername(username);

        Assertions.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }


}
