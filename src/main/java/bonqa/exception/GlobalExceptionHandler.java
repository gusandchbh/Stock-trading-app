package bonqa.exception;

import bonqa.portfoliostock.PortfolioStock;
import bonqa.portfoliostock.exception.InsufficientFundsException;
import bonqa.portfoliostock.exception.ResourceNotFoundException;
import bonqa.user.exception.NotAuthorizedException;
import bonqa.user.exception.UserNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(NotAuthorizedException.class)
    public ResponseEntity<String> handleNotAuthorizedException(NotAuthorizedException e) {
        LOGGER.error("NotAuthorizedException occurred", e);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
    }

    @ExceptionHandler(InsufficientFundsException.class)
    public ResponseEntity<String> handleInsufficientFundsException(InsufficientFundsException e) {
        LOGGER.error("InsufficientFundsException occurred", e);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

    @ExceptionHandler(PortfolioStock.InsufficientSharesException.class)
    public ResponseEntity<String> handleInsufficientSharesException(PortfolioStock.InsufficientSharesException e) {
        LOGGER.error("InsufficientSharesException occurred", e);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<String> handleAccessDeniedException(AccessDeniedException ex) {
        LOGGER.error("AccessDeniedException occurred", ex);
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ex.getMessage());
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<String> handleUserNotFoundException(UserNotFoundException e) {
        LOGGER.error("UserNotFoundException occurred", e);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception e) {
        LOGGER.error("Unhandled exception caught: " + e.getClass().getSimpleName(), e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body("An internal server error occurred. Please try again later.");
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<String> handleResourceNotFoundException(ResourceNotFoundException e) {
        LOGGER.error("ResourceNotFoundException occurred", e);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<String> handleDataIntegrityViolationException(DataIntegrityViolationException e) {
        LOGGER.error("DataIntegrityViolationException occurred", e);
        return ResponseEntity.status(HttpStatus.CONFLICT).body("The operation could not be completed due to a data integrity violation.");
    }

}

