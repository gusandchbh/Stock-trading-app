package bonqa.portfoliostock.exception;

public class ResourceNotFoundException extends IllegalArgumentException {
  public ResourceNotFoundException(String userNotFound) {
    super(userNotFound);
  }


}
