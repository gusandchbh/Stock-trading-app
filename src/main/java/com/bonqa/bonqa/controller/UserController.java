package com.bonqa.bonqa.controller;

import com.bonqa.bonqa.model.BankUser;
import com.bonqa.bonqa.requests.CreateUserRequest;
import com.bonqa.bonqa.requests.UpdateUserRequest;
import com.bonqa.bonqa.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(path = {"/users"})
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    Iterable<BankUser> all() {
        return userService.getUsers();
    }

    @PostMapping("/create")
    public ResponseEntity<BankUser> create(@RequestBody @Valid CreateUserRequest request) {
        try {
            BankUser bankUser = userService.createUser(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(bankUser);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteById(@PathVariable Long id) {
        try {
            BankUser bankUser = userService.getUserById(id);
            if (bankUser == null) {
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
    public ResponseEntity<BankUser> updateById(@RequestBody UpdateUserRequest updateUserRequest, @PathVariable Long id) {
        try {
            BankUser bankUser = userService.updateUser(updateUserRequest, id);
            if (bankUser == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok().body(bankUser);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<BankUser> fetchByID(@PathVariable Long id) {
        try {
            BankUser bankUser = userService.getUserById(id);
            if (bankUser == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
            return ResponseEntity.ok().body(bankUser);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/search")
    public ResponseEntity<BankUser> fetchByUsername(@RequestParam(value = "username") String username) {
        try {
            BankUser bankUser = userService.getUserByUsername(username);
            if (bankUser == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
            return ResponseEntity.ok().body(bankUser);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}

