package bonqa.authentication.exception;

public class NotLoggedInException extends RuntimeException {

    public NotLoggedInException(String message) {
        super(message);
    }
}
