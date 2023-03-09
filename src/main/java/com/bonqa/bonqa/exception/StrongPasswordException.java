package com.bonqa.bonqa.exception;

import jakarta.validation.ValidationException;
import java.util.List;

public class StrongPasswordException extends ValidationException {

  private final List<String> messages;

  public StrongPasswordException(List<String> messages) {
    this.messages = messages;
  }

  @Override
  public String getMessage() {
    if (messages.isEmpty()) {
      return "Invalid password";
    } else {
      return String.join(", ", messages);
    }
  }

  public List<String> getMessages() {
    return messages;
  }
}
