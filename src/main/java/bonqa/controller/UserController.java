package bonqa.controller;

import bonqa.domain.model.data.request.UpdateEmailRequest;
import bonqa.domain.model.data.request.UpdatePasswordRequest;
import bonqa.domain.repository.UserRepository;
import bonqa.domain.user.UserService;
import bonqa.dto.UserDTO;
import jakarta.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = {"/users"})
public class UserController {

  private final UserRepository userRepository;
  private final UserService userService;

  @Autowired
  public UserController(UserRepository userRepository, UserService userService) {
    this.userRepository = userRepository;
    this.userService = userService;
  }

  @GetMapping("/")
  public List<UserDTO> all() {
    return userService.getAllUsers();
  }


  @DeleteMapping("/delete/{id}")
  public ResponseEntity<String> deleteById(@PathVariable Long id) {
    userService.deleteUserById(id);
    return new ResponseEntity<>("User deleted!", HttpStatus.OK);
  }

  @PreAuthorize("hasRole('ADMIN')")
  @DeleteMapping("/delete/all")
  public ResponseEntity<String> deleteAll() {
    userRepository.deleteAll();
    return new ResponseEntity<>("All users deleted!", HttpStatus.OK);
  }

  @PutMapping("/update/email/{id}")
  public ResponseEntity<String> updateEmailById(
      @Valid @RequestBody UpdateEmailRequest updateEmailRequest,
      @PathVariable Long id) {
    userService.updateEmail(updateEmailRequest, id);
    return ResponseEntity.ok().body("Email successfully updated!");

  }

  @PutMapping("/update/password/{id}")
  public ResponseEntity<String> updatePasswordById(
      @Valid @RequestBody UpdatePasswordRequest updatePasswordRequest,
      @PathVariable Long id) {
    userService.updatePassword(updatePasswordRequest, id);
    return ResponseEntity.ok().body("Password successfully updated!");
  }


  @GetMapping("/{id}")
  public ResponseEntity<UserDTO> fetchById(@PathVariable Long id) {
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
    return ResponseEntity.badRequest().body(String.join(", ", messages));
  }

}

