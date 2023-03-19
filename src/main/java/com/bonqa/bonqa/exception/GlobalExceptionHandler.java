package com.bonqa.bonqa.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
  @ExceptionHandler(NotAuthorizedException.class)
  public ResponseEntity<String> handleNotAuthorizedException(NotAuthorizedException e) {
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
        .body(e.getMessage());
  }

  @ExceptionHandler(AccessDeniedException.class)
  public ResponseEntity<String> handleAccessDeniedException(
      AccessDeniedException ex) {
    return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ex.getMessage());
  }

  @ExceptionHandler(UserNotFoundException.class)
  public ResponseEntity<String> handleUserNotFoundException(UserNotFoundException e) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
  }

  // Add a generic exception handler for all other exceptions
  @ExceptionHandler(Exception.class)
  public ResponseEntity<String> handleException(Exception e) {
    System.err.println("Unhandled exception caught: " + e.getClass().getSimpleName());
    e.printStackTrace();

    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body("An internal server error occurred. Please try again later.");
  }

}

