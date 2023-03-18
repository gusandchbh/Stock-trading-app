package com.bonqa.bonqa.unit_tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import com.bonqa.bonqa.controller.AuthenticationController;
import com.bonqa.bonqa.domain.authentication.AuthenticationService;
import com.bonqa.bonqa.domain.model.data.request.AuthenticationRequest;
import com.bonqa.bonqa.domain.model.data.request.RegisterRequest;
import com.bonqa.bonqa.exception.BadRequestException;
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
class AuthenticationControllerTest {

  @Mock
  private AuthenticationService authenticationService;

  @InjectMocks
  private AuthenticationController authenticationController;

  private RegisterRequest registerRequest;
  private AuthenticationRequest authenticationRequest;

  @BeforeEach
  void setUp() {
    registerRequest = new RegisterRequest("username", "password", "email@example.com");
    authenticationRequest = new AuthenticationRequest("username", "password");
  }

  @Test
  @DisplayName("Register user successfully")
  void testRegisterUser() throws BadRequestException {
    String token = "token";
    when(authenticationService.register(registerRequest)).thenReturn(token);

    ResponseEntity<String> response = authenticationController.registerUser(registerRequest);

    assertEquals(HttpStatus.CREATED, response.getStatusCode());
    assertEquals(token, response.getBody());
  }

  @Test
  @DisplayName("Register user with invalid input")
  void testRegisterUserWithInvalidInput() throws BadRequestException {
    doThrow(new BadRequestException("Invalid input")).when(authenticationService)
        .register(registerRequest);

    ResponseEntity<String> response = authenticationController.registerUser(registerRequest);

    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    assertEquals("Invalid input", response.getBody());
  }

  @Test
  @DisplayName("Authenticate user successfully")
  void testAuthenticate() {
    String token = "token";
    when(authenticationService.authenticate(authenticationRequest)).thenReturn(token);

    ResponseEntity<String> response = authenticationController.authenticate(authenticationRequest);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(token, response.getBody());
  }

  @Test
  @DisplayName("Authenticate user with invalid credentials")
  void testAuthenticateWithInvalidCredentials() {
    when(authenticationService.authenticate(authenticationRequest)).thenThrow(
        new BadRequestException("Invalid credentials"));
    try {
      authenticationController.authenticate(authenticationRequest);
      fail("Expected BadRequestException to be thrown");
    } catch (BadRequestException e) {
      assertEquals("Invalid credentials", e.getMessage());
    }
  }

}
