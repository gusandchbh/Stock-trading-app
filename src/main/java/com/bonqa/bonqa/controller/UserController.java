package com.bonqa.bonqa.controller;

import com.bonqa.bonqa.domain.model.User;
import com.bonqa.bonqa.domain.model.data.request.LoginRequest;
import com.bonqa.bonqa.domain.model.data.request.RegisterRequest;
import com.bonqa.bonqa.domain.model.data.request.UpdateUserRequest;
import com.bonqa.bonqa.domain.repository.UserRepository;
import com.bonqa.bonqa.domain.user.UserService;
import com.bonqa.bonqa.exception.BadRequestException;
import jakarta.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
  public Iterable<User> all() {
    return userRepository.findAll();
  }

  @PostMapping("/login")
  public ResponseEntity<String> login(@RequestBody LoginRequest userLogin)
      throws AuthenticationException {
    try {
      String token = userService.loginUser(userLogin);
      return ResponseEntity.status(HttpStatus.OK).body(token);
    } catch (AuthenticationException e) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
    }
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<String> handleValidationException(MethodArgumentNotValidException ex) {
    List<String> messages = new ArrayList<>();
    for (ObjectError error : ex.getBindingResult().getAllErrors()) {
      messages.add(error.getDefaultMessage());
    }
    return ResponseEntity.badRequest().body(String.join(", ", messages));
  }

  @PostMapping("/register")
  public ResponseEntity<String> registerUser(@Valid @RequestBody RegisterRequest registerRequest)
      throws AuthenticationException {
    try {
      userService.registerUser(registerRequest);
    } catch (BadRequestException e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }
    return ResponseEntity.status(HttpStatus.CREATED).body("Registration was successful!");
  }

  @DeleteMapping("/delete/{id}")
  public ResponseEntity<String> deleteById(@PathVariable Long id) {
    try {
      userRepository.deleteById(id);
      return new ResponseEntity<>("User deleted!", HttpStatus.OK);
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }

  @DeleteMapping("/delete/all")
  public ResponseEntity<String> deleteAll() {
    try {
      userRepository.deleteAll();
      return new ResponseEntity<>("All users deleted!", HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>("Failed to delete all users", HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @PutMapping("/update/{id}")
  public ResponseEntity<Void> updateById(@RequestBody UpdateUserRequest updateUserRequest,
                                         @PathVariable Long id) {
    try {
      userService.updateUser(updateUserRequest, id);
      return ResponseEntity.ok().build();
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }

  @GetMapping("/{id}")
  public ResponseEntity<User> fetchById(@PathVariable Long id) {
    try {
      Optional<User> user = userRepository.findById(id);
      return user.map(value -> ResponseEntity.ok().body(value))
          .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }


}

