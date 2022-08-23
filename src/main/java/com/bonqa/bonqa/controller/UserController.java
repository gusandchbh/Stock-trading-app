package com.bonqa.bonqa.controller;

import com.bonqa.bonqa.model.User;
import com.bonqa.bonqa.repository.UserRepository;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(path = {"/users"})
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/")
    Iterable<User> all() {
        return userRepository.findAll();
    }

    @PostMapping("/create")
    public User create(@RequestBody JsonNode payload) {
        var user = new User();
        user.setUsername(payload.get("username").toString());
        user.setPassword(payload.get("password").toString());

        var uuid = UUID.randomUUID();
        user.setId(uuid);

        userRepository.save(user);

        return user;
    }

    // @TOOD: deleteById expects long, we use UUID internally
    // create custom repository method to delete user by UUID (or start storing integer IDs?)
    @DeleteMapping("/delete/{userId}")
    public void delete(@PathVariable Long userId) {
        userRepository.deleteById(userId);
    }

    @PutMapping("/update/{userId}/{payload}")
    public void update(@RequestBody JsonNode payload, @PathVariable Long userId) {
        var user = userRepository.findById(userId);

        user.map(u -> {
            u.setUsername(payload.get("username").toString());
            u.setPassword(payload.get("password").toString());

            userRepository.save(u);

            return null;
        });
    }

    @GetMapping("/{username}")
    User fetchByUsername(@PathVariable String username) {
        return userRepository.findByUsername(username);
    }
}
