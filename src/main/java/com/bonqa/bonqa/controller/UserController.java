package com.bonqa.bonqa.controller;

import com.bonqa.bonqa.model.User;
import com.bonqa.bonqa.repository.UserRepository;
import com.bonqa.bonqa.requests.CreateUserRequest;
import com.bonqa.bonqa.requests.UpdateUserRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.Optional;

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
        try {
            User user = new User();
            user.setUsername(request.getUsername());
            user.setPassword(request.getPassword());
            user.setRole(request.getRole());
            user.setCreatedTime(LocalDateTime.now());
            userRepository.save(user);
            return ResponseEntity.status(HttpStatus.CREATED).body(user);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteById(@PathVariable Long id) {
        try {
            Optional<User> optionalUser = userRepository.findById(id);
            if (optionalUser.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            userRepository.deleteById(id);
            return new ResponseEntity<>(
                    "User deleted!",
                    HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @DeleteMapping("/delete/all")
    public ResponseEntity<String> deleteAll() {
        userRepository.deleteAll();
        return new ResponseEntity<>(
                "All users deleted!",
                HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<User> updateById(@RequestBody UpdateUserRequest updateUserRequest, @PathVariable Long id) {
        String password = updateUserRequest.getPassword();
        try {
            Optional<User> optionalUser = userRepository.findById(id);
            if (optionalUser.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            User user = optionalUser.get();
            user.setPassword(password);
            userRepository.save(user);
            return ResponseEntity.ok().body(user);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> fetchByID(@PathVariable Long id) {
        try {
            Optional<User> user = userRepository.findById(id);
            return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    @GetMapping("/search")
    public ResponseEntity<User> fetchByUsername(@RequestParam(value = "username") String username) {
        try {
            Optional<User> user = userRepository.findOneByUsername(username);
            return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
