package com.bonqa.bonqa.service;

import com.bonqa.bonqa.model.BankUser;
import com.bonqa.bonqa.requests.CreateUserRequest;
import com.bonqa.bonqa.requests.UpdateUserRequest;

public interface UserService {
    BankUser createUser(CreateUserRequest request);
    BankUser updateUser(UpdateUserRequest request, Long id);
    Iterable<BankUser> getUsers();
    BankUser getUserById(Long id);
    BankUser getUserByUsername(String username);
    void deleteUserById(Long id);
    void deleteAllUsers();
}
