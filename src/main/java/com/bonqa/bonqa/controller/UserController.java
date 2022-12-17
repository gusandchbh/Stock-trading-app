package com.bonqa.bonqa.controller;

import com.bonqa.bonqa.model.Role;
import com.bonqa.bonqa.model.User;
import com.bonqa.bonqa.repository.UserRepository;
import com.bonqa.bonqa.requests.CreateUserRequest;
import com.fasterxml.jackson.databind.JsonNode;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping(path = {"/users"})
public class UserController {

    private final UserRepository userRepository;
    @Autowired
    public UserController(UserRepository userRepository){
        this.userRepository = userRepository;
    }


    @GetMapping("/")
    Iterable<User> all() {
        return userRepository.findAll();
    }

    @PostMapping("/create")
    public ResponseEntity<User> create(@RequestBody @Valid CreateUserRequest request) {
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(request.getPassword());
        user.setRole(request.getRole());
        user.setCreatedTime(LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    // @TOOD: deleteById expects long, we use UUID internally
    // create custom repository method to delete user by UUID (or start storing integer IDs?)
    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<String> deleteById(@PathVariable("userId") UUID userId) {
        var user = userRepository.findById(userId);
        if (user == null) {
            return new ResponseEntity<>(
                    "User not found.",
                    HttpStatus.NOT_FOUND);
        }
        userRepository.deleteById(userId);
        return new ResponseEntity<>(
                "User deleted!",
                HttpStatus.OK);
    }
    @DeleteMapping("/delete/all")
    public ResponseEntity<String> deleteAll() {
        userRepository.deleteAll();
        return new ResponseEntity<>(
                "All users deleted!",
                HttpStatus.OK);
    }

    @PutMapping("/update/{userId}/")
    public void update(@RequestBody JsonNode payload, @PathVariable Long userId) {
        var user = userRepository.findById(userId);
        user.map(u -> {
            u.setUsername(payload.get("username").toString());
            u.setPassword(payload.get("password").toString());
            userRepository.save(u);
            return null;
        });
    }

    @GetMapping("/{id}")
    Optional<User> fetchByID(@PathVariable Long id) {
        return userRepository.findById(id);
    }

    @GetMapping()
    User fetchByUsername(@RequestParam(value = "username") String username) {
        return userRepository.findByUsername(username);
    }
}
