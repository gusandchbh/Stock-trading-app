package com.bonqa.bonqa.controller;

import com.bonqa.bonqa.domain.authentication.AuthenticationService;
import com.bonqa.bonqa.domain.model.data.request.AuthenticationRequest;
import com.bonqa.bonqa.domain.model.data.request.RegisterRequest;
import com.bonqa.bonqa.exception.BadRequestException;
import jakarta.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

  private final AuthenticationService service;

  @PostMapping("/register")
  public ResponseEntity<String> registerUser(@Valid @RequestBody RegisterRequest registerRequest)
      throws AuthenticationException {
    try {
      String token = service.register(registerRequest);
      return ResponseEntity.status(HttpStatus.CREATED).body(token);
    } catch (BadRequestException e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }
  }

  @PostMapping("/authenticate")
  public ResponseEntity<String> authenticate(@RequestBody AuthenticationRequest userLogin)
      throws AuthenticationException {
    try {
      String token = service.authenticate(userLogin);
      return ResponseEntity.status(HttpStatus.OK).body(token);
    } catch (AuthenticationException e) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
    }
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<String> handleValidationException(MethodArgumentNotValidException ex) {
    List<String> messages = new ArrayList<>();
    for (ObjectError error : ex.getBindingResult().getAllErrors()) {
      messages.add(error.getDefaultMessage());
    }
    return ResponseEntity.badRequest().body(String.join(", ", messages));
  }


}