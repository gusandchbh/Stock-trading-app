package com.bonqa.bonqa.exception;

public class InsufficientSharesException extends RuntimeException {
  public InsufficientSharesException(String message) {
    super(message);
  }
}