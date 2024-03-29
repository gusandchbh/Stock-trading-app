package bonqa.user;

import bonqa.user.request.UpdateEmailRequest;
import bonqa.user.request.UpdatePasswordRequest;
import jakarta.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = {"/api/v1/users"})
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    private final UserRepository userRepository;
    private final UserService userService;
    private final AuthorizationService authorizationService;

    @Autowired
    public UserController(
            UserRepository userRepository, UserService userService, AuthorizationService authorizationService) {
        this.userRepository = userRepository;
        this.userService = userService;
        this.authorizationService = authorizationService;
    }

    @GetMapping("/")
    public List<UserDTO> all() {
        logger.info("Fetching all users");
        return userService.getAllUsers();
    }

    @PutMapping("/update/email/{id}")
    public ResponseEntity<?> updateEmailById(
            @Valid @RequestBody UpdateEmailRequest updateEmailRequest, @PathVariable Long id) {
        logger.info("Updating email for user with ID: {}", id);

        if (authorizationService.isAuthenticatedUser(id)) {
            userService.updateEmail(updateEmailRequest, id);
            return ResponseEntity.ok().body("Email successfully updated!");
        }
        return new ResponseEntity<>("You are not authorized to update this user's email", HttpStatus.FORBIDDEN);
    }

    @PutMapping("/update/password/{id}")
    public ResponseEntity<String> updatePasswordById(
            @Valid @RequestBody UpdatePasswordRequest updatePasswordRequest, @PathVariable Long id) {
        logger.info("Updating password for user with ID: {}", id);
        logger.error("Password: {} ", updatePasswordRequest.getPassword());

        if (updatePasswordRequest.getPassword() == null) {
            return new ResponseEntity<>("Password cannot be null", HttpStatus.BAD_REQUEST);
        }

        if (authorizationService.isAuthenticatedUser(id)) {
            userService.updatePassword(updatePasswordRequest, id);
            return ResponseEntity.ok().body("Password successfully updated!");
        }
        return new ResponseEntity<>("You are not authorized to update this user's password", HttpStatus.FORBIDDEN);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteById(@PathVariable Long id) {
        logger.info("Deleting user with ID: {}", id);

        if (authorizationService.isAuthenticatedUser(id)) {
            userService.deleteUserById(id);
            return new ResponseEntity<>("User deleted!", HttpStatus.OK);
        }
        return new ResponseEntity<>("You are not authorized to delete this user", HttpStatus.FORBIDDEN);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete/all")
    public ResponseEntity<String> deleteAll() {
        logger.info("Deleting all users");
        userRepository.deleteAll();
        return new ResponseEntity<>("All users deleted!", HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> fetchById(@PathVariable Long id) {
        logger.info("Fetching user with ID: {}", id);
        Optional<UserDTO> user = userService.getUser(id);
        return user.map(value -> ResponseEntity.ok().body(value))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
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
