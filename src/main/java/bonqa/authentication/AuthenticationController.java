package bonqa.authentication;

import bonqa.authentication.exception.BadRequestException;
import bonqa.authentication.request.AuthenticationRequest;
import bonqa.authentication.request.RegisterRequest;
import jakarta.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthenticationController {

    private static final Logger logger = LoggerFactory.getLogger(AuthenticationController.class);

    private final AuthenticationService authenticationService;

    @Autowired
    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@Valid @RequestBody RegisterRequest registerRequest)
            throws AuthenticationException {
        try {
            logger.info("Attempting to register user: {}", registerRequest.getUsername());
            String token = authenticationService.register(registerRequest);
            logger.info("User registered successfully: {}", registerRequest.getUsername());
            return ResponseEntity.status(HttpStatus.CREATED).body(token);
        } catch (BadRequestException e) {
            logger.error("Failed to register user: {}. {}", registerRequest.getUsername(), e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/authenticate")
    public ResponseEntity<String> authenticate(@RequestBody AuthenticationRequest userLogin)
            throws AuthenticationException {
        try {
            logger.info("Attempting to authenticate user: {}", userLogin.getUsername());
            String token = authenticationService.authenticate(userLogin);
            logger.info("User {} authenticated successfully", userLogin.getUsername());
            return ResponseEntity.status(HttpStatus.OK).body(token);
        } catch (AuthenticationException e) {
            logger.error("Failed to authenticate user {} : Invalid username or password", userLogin.getUsername());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
        }
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleValidationException(MethodArgumentNotValidException ex) {
        List<String> messages = new ArrayList<>();
        for (ObjectError error : ex.getBindingResult().getAllErrors()) {
            messages.add(error.getDefaultMessage());
        }
        logger.error("Validation error: {}", messages.get(0));
        return ResponseEntity.badRequest().body(String.join(", ", messages));
    }
}
