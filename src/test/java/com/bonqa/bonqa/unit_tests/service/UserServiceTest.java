package com.bonqa.bonqa.unit_tests.service;

import bonqa.user.User;
import bonqa.user.UserRepository;
import bonqa.user.UserService;
import bonqa.user.exception.UserNotFoundException;
import bonqa.user.request.UpdateEmailRequest;
import bonqa.user.request.UpdatePasswordRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

  @InjectMocks
  private UserService userService;

  @Mock
  private UserRepository userRepository;

  @Mock
  private PasswordEncoder passwordEncoder;

  @Test
  void testUpdateEmail_UserNotFound() {
    when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

    UpdateEmailRequest updateEmailRequest = new UpdateEmailRequest();

    assertThrows(UserNotFoundException.class, () -> userService.updateEmail(updateEmailRequest, 1L));
  }

  @Test
  void testUpdateEmail_Success() {
    User user = new User();
    user.setUsername("testUser");
    when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));

    assertDoesNotThrow(() -> userService.updateEmail(new UpdateEmailRequest(), 1L));
  }

  @Test
  void testUpdatePassword_UserNotFound() {
    when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

    UpdatePasswordRequest updatePasswordRequest = new UpdatePasswordRequest();

    assertThrows(UserNotFoundException.class, () -> userService.updatePassword(updatePasswordRequest, 1L));
  }


  @Test
  void testUpdatePassword_Success() {
    User user = new User();
    user.setUsername("testUser");
    when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));

    UpdatePasswordRequest updatePasswordRequest = new UpdatePasswordRequest();
    updatePasswordRequest.setPassword("newPassword");

    userService.updatePassword(updatePasswordRequest, 1L);

    verify(passwordEncoder).encode("newPassword");
  }


  @Test
  void testDeleteUserById_UserNotFound() {
    when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

    assertThrows(UserNotFoundException.class, () -> userService.deleteUserById(1L));
  }

  @Test
  void testDeleteUserById_Success() {
    User user = new User();
    user.setUsername("testUser");
    when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));

    assertDoesNotThrow(() -> userService.deleteUserById(1L));
  }


}
