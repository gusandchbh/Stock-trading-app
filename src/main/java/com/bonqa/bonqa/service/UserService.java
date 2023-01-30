package com.bonqa.bonqa.service;

import com.bonqa.bonqa.model.User;
import com.bonqa.bonqa.requests.LoginRequest;
import com.bonqa.bonqa.requests.RegisterRequest;
import com.bonqa.bonqa.requests.UpdateUserRequest;

public interface UserService {
    User registerUser(RegisterRequest registerRequest);

    String loginUser(LoginRequest loginRequest);
    User updateUser(UpdateUserRequest request, Long id);
    Iterable<User> getUsers();
    User getUserById(Long id);
    User getUserByUsername(String username);
    void deleteUserById(Long id);
    void deleteAllUsers();

    User createUser(RegisterRequest registerRequest);
}
