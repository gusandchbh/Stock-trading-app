package com.bonqa.bonqa.controller;

import com.bonqa.bonqa.domain.model.User;
import com.bonqa.bonqa.domain.model.data.request.UpdateEmailRequest;
import com.bonqa.bonqa.domain.model.data.request.UpdateNamesRequest;
import com.bonqa.bonqa.domain.model.data.request.UpdatePasswordRequest;
import com.bonqa.bonqa.domain.repository.UserRepository;
import com.bonqa.bonqa.domain.user.UserService;
import com.bonqa.bonqa.dto.UserDTO;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
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

  @PutMapping("/update/names/{id}")
  public ResponseEntity<String> updateNamesById(
      @Valid @RequestBody UpdateNamesRequest updateNamesRequest,
      @PathVariable Long id) {
    try {
      userService.updateNames(updateNamesRequest, id);
      return ResponseEntity.ok().body("Names have been successfully updated!");
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }

  @PutMapping("/update/email/{id}")
  public ResponseEntity<String> updateEmailById(
      @Valid @RequestBody UpdateEmailRequest updateEmailRequest,
      @PathVariable Long id) {
    try {
      userService.updateEmail(updateEmailRequest, id);
      return ResponseEntity.ok().body("Email successfully updated!");
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }

  @PutMapping("/update/password/{id}")
  public ResponseEntity<String> updatePasswordById(
      @Valid @RequestBody UpdatePasswordRequest updatePasswordRequest,
      @PathVariable Long id) {
    try {
      userService.updatePassword(updatePasswordRequest, id);
      return ResponseEntity.ok().body("Password successfully updated!");
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

