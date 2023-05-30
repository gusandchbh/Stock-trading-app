package com.bonqa.bonqa.unit_tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import bonqa.user.AuthorizationService;
import bonqa.user.UserController;
import bonqa.user.UserDTO;
import bonqa.user.UserRepository;
import bonqa.user.UserService;
import bonqa.user.request.UpdateEmailRequest;
import bonqa.user.request.UpdatePasswordRequest;
import java.util.Arrays;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
@ExtendWith(MockitoExtension.class)
class UserControllerTest {

  @InjectMocks
  private UserController userController;

  @Mock
  private UserService userService;

  @Mock
  private AuthorizationService authorizationService;

  @Mock
  private UserRepository userRepository;

  @Test
  void testAll() {
    UserDTO user1 = new UserDTO();
    user1.setId(1L);
    user1.setEmail("test1@example.com");

    UserDTO user2 = new UserDTO();
    user2.setId(2L);
    user2.setEmail("test2@example.com");

    when(userService.getAllUsers()).thenReturn(Arrays.asList(user1, user2));

    assertEquals(2, userController.all().size());
  }

  @Test
  void testUpdateEmailById() {
    Long id = 1L;
    UpdateEmailRequest request = new UpdateEmailRequest();
    request.setEmail("newemail@example.com");

    when(authorizationService.isAuthenticatedUser(id)).thenReturn(true);

    ResponseEntity<?> response = userController.updateEmailById(request, id);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals("Email successfully updated!", response.getBody());
  }

  @Test
  void testDeleteById_NotAuthorized() {
    Long id = 1L;
    when(authorizationService.isAuthenticatedUser(id)).thenReturn(false);

    ResponseEntity<String> response = userController.deleteById(id);

    assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    assertEquals("You are not authorized to delete this user", response.getBody());
  }

  @Test
  void testDeleteAll() {
    ResponseEntity<String> response = userController.deleteAll();

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals("All users deleted!", response.getBody());
  }

  @Test
  void testFetchById() {
    Long id = 1L;
    UserDTO user = new UserDTO();
    user.setId(id);
    user.setEmail("test@example.com");

    when(userService.getUser(id)).thenReturn(Optional.of(user));

    ResponseEntity<UserDTO> response = userController.fetchById(id);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(id, response.getBody().getId());
    assertEquals("test@example.com", response.getBody().getEmail());
  }

  @Test
  void testFetchById_NotFound() {
    Long id = 1L;
    when(userService.getUser(id)).thenReturn(Optional.empty());

    ResponseEntity<UserDTO> response = userController.fetchById(id);

    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
  }

  @Test
  void testDeleteById_WhenAuthorized() {
    Long id = 1L;

    when(authorizationService.isAuthenticatedUser(id)).thenReturn(true);

    ResponseEntity<String> response = userController.deleteById(id);

    assertEquals(HttpStatus.OK, response.getStatusCode());

    assertEquals("User deleted!", response.getBody());
  }

  @Test
  void testUpdatePasswordById() {
    Long id = 1L;
    UpdatePasswordRequest request = new UpdatePasswordRequest();
    request.setPassword("newPassword");

    when(authorizationService.isAuthenticatedUser(id)).thenReturn(true);

    ResponseEntity<String> response = userController.updatePasswordById(request, id);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals("Password successfully updated!", response.getBody());
  }

  @Test
  void testDeleteById_NotExist() {
    Long id = 1L;
    when(authorizationService.isAuthenticatedUser(id)).thenReturn(true);
    doThrow(new IllegalArgumentException("User not found")).when(userService).deleteUserById(id);

    assertThrows(IllegalArgumentException.class, () -> userController.deleteById(id));
  }

  @Test
  void testUpdateEmailById_NotExist() {
    Long id = 1L;
    UpdateEmailRequest request = new UpdateEmailRequest();
    request.setEmail("newemail@example.com");
    when(authorizationService.isAuthenticatedUser(id)).thenReturn(true);
    doThrow(new IllegalArgumentException("User not found")).when(userService).updateEmail(request, id);

    assertThrows(IllegalArgumentException.class, () -> userController.updateEmailById(request, id));
  }

}