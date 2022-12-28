package com.bonqa.bonqa.service;

import com.bonqa.bonqa.model.User;
import com.bonqa.bonqa.requests.CreateUserRequest;
import com.bonqa.bonqa.requests.UpdateUserRequest;

public interface UserService {
    User createUser(CreateUserRequest request);
    User updateUser(UpdateUserRequest request, Long id);
    Iterable<User> getUsers();
    User getUserById(Long id);
    User getUserByUsername(String username);
    void deleteUserById(Long id);
    void deleteAllUsers();
}
