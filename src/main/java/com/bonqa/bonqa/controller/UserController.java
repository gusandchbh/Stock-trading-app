package com.bonqa.bonqa.controller;

import com.bonqa.bonqa.model.User;
import com.bonqa.bonqa.requests.CreateUserRequest;
import com.bonqa.bonqa.requests.UpdateUserRequest;
import com.bonqa.bonqa.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(path = {"/users"})
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    Iterable<User> all() {
        return userService.getUsers();
    }

    @PostMapping("/create")
    public ResponseEntity<User> create(@RequestBody @Valid CreateUserRequest request) {
        try {
            User user = userService.createUser(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(user);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteById(@PathVariable Long id) {
        try {
            User user = userService.getUserById(id);
            if (user == null) {
                return ResponseEntity.notFound().build();
            }
            userService.deleteUserById(id);
            return new ResponseEntity<>(
                    "User deleted!",
                    HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/delete/all")
    public ResponseEntity<String> deleteAll() {
        userService.deleteAllUsers();
        return new ResponseEntity<>(
                "All users deleted!",
                HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<User> updateById(@RequestBody UpdateUserRequest updateUserRequest, @PathVariable Long id) {
        try {
            User user = userService.updateUser(updateUserRequest, id);
            if (user == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok().body(user);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> fetchByID(@PathVariable Long id) {
        try {
            User user = userService.getUserById(id);
            if (user == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
            return ResponseEntity.ok().body(user);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping(value = "/", params = "username")
    public ResponseEntity<User> fetchByUsername(@RequestParam(value = "username") String username) {
        try {
            User user = userService.getUserByUsername(username);
            if (user == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
            return ResponseEntity.ok().body(user);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}

