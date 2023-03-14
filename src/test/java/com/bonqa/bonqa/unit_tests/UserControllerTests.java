/*
package com.bonqa.bonqa.unit_tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.bonqa.bonqa.controller.UserController;
import com.bonqa.bonqa.domain.model.User;
import com.bonqa.bonqa.domain.model.data.request.LoginRequest;
import com.bonqa.bonqa.domain.repository.UserRepository;
import com.bonqa.bonqa.domain.user.UserService;
import com.bonqa.bonqa.exception.InvalidCredentialsException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

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
    when(userService.loginUser(loginRequest)).thenThrow(
        new InvalidCredentialsException("Invalid username or password"));

    ResponseEntity<String> response = userController.login(loginRequest);

    assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    assertEquals("Invalid username or password", response.getBody());
  }

  @Test
  void testDeleteById_Success() {
    Long id = 123L;

    ResponseEntity<String> response = userController.deleteById(id);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals("User deleted!", response.getBody());

    verify(userRepository, times(1)).deleteById(id);
  }

  @Test
  void testDeleteById_Exception() {
    Long id = 123L;

    doThrow(new RuntimeException("Failed to delete user")).when(userRepository).deleteById(id);

    ResponseEntity<String> response = userController.deleteById(id);

    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());

    verify(userRepository, times(1)).deleteById(id);
  }

  @Test
  void testDeleteAll_Success() {
    doNothing().when(userRepository).deleteAll();

    ResponseEntity<String> response = userController.deleteAll();

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals("All users deleted!", response.getBody());

    verify(userRepository, times(1)).deleteAll();
  }

  @Test
  void testDeleteAll_Failure() {
    doThrow(new RuntimeException("Failed to delete all users")).when(userRepository).deleteAll();

    ResponseEntity<String> response = userController.deleteAll();

    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    assertEquals("Failed to delete all users", response.getBody());

    verify(userRepository, times(1)).deleteAll();
  }


  @Test
  void testFetchById_Success() {
    Long id = 123L;
    User user = new User();
    user.setId(id);

    when(userRepository.findById(id)).thenReturn(Optional.of(user));

    ResponseEntity<User> response = userController.fetchById(id);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(user, response.getBody());

    verify(userRepository, times(1)).findById(id);
  }

  @Test
  void testFetchById_NotFound() {
    Long id = 123L;

    when(userRepository.findById(id)).thenReturn(Optional.empty());

    ResponseEntity<User> response = userController.fetchById(id);

    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    assertNull(response.getBody());

    verify(userRepository, times(1)).findById(id);
  }

  @Test
  void testFetchById_Exception() {
    Long id = 123L;

    when(userRepository.findById(id)).thenThrow(new RuntimeException("Failed to fetch user"));

    ResponseEntity<User> response = userController.fetchById(id);

    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());

    verify(userRepository, times(1)).findById(id);
  }

}
*/
