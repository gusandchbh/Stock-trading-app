package bonqa.authentication;

import bonqa.authentication.exception.BadRequestException;
import bonqa.authentication.exception.NotLoggedInException;
import bonqa.authentication.request.AuthenticationRequest;
import bonqa.authentication.request.RegisterRequest;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthenticationController {

    private static final Logger logger = LoggerFactory.getLogger(AuthenticationController.class);

    private final AuthenticationService authenticationService;

    private final LogoutService logoutService;

    @Autowired
    public AuthenticationController(AuthenticationService authenticationService, LogoutService logoutService) {
        this.authenticationService = authenticationService;
        this.logoutService = logoutService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@Valid @RequestBody RegisterRequest registerRequest)
            throws AuthenticationException {
        try {
            logger.info("Attempting to register user: {}", registerRequest.getUsername());
            String token = authenticationService.register(registerRequest);
            logger.info("User registered successfully");
            return ResponseEntity.status(HttpStatus.CREATED).body(token);
        } catch (BadRequestException e) {
            logger.error("Failed to register user: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/authenticate")
    public ResponseEntity<String> authenticate(@RequestBody AuthenticationRequest userLogin)
            throws AuthenticationException {
        try {
            logger.info("Attempting to authenticate user: {}", userLogin.getUsername());
            String token = authenticationService.authenticate(userLogin);
            logger.info("User authenticated successfully");
            return ResponseEntity.status(HttpStatus.OK).body(token);
        } catch (AuthenticationException e) {
            logger.error("Failed to authenticate user: Invalid username or password");
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

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request, HttpServletResponse response) {
        try {
            logger.info("Attempting to logout user");
            logoutService.logout(
                    request, response, SecurityContextHolder.getContext().getAuthentication());
            logger.info("User logged out successfully");
            return ResponseEntity.ok().body("Logged out successfully!");
        } catch (NotLoggedInException e) {
            logger.error("Failed to logout user: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
