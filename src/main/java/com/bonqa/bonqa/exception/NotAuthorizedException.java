package com.bonqa.bonqa.exception;

public class NotAuthorizedException extends RuntimeException {

  public NotAuthorizedException(String message) {
    super(message);
  }
}
