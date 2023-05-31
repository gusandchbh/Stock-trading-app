package bonqa.portfoliostock.exception;

public class InsufficientSharesException extends RuntimeException {
  public InsufficientSharesException(String message) {
    super(message);
  }
}

