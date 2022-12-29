package com.bonqa.bonqa.service;

import com.bonqa.bonqa.model.BankUser;
import com.bonqa.bonqa.repository.UserRepository;
import com.bonqa.bonqa.requests.CreateUserRequest;
import com.bonqa.bonqa.requests.UpdateUserRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public BankUser createUser(CreateUserRequest request) {
        BankUser bankUser = new BankUser();
        bankUser.setUsername(request.getUsername());
        bankUser.setPassword(request.getPassword());
        bankUser.setRole(request.getRole());
        bankUser.setCreatedTime(LocalDateTime.now());
        return userRepository.save(bankUser);
    }

    @Override
    public BankUser updateUser(UpdateUserRequest request, Long id) {
        Optional<BankUser> optionalUser = userRepository.findById(id);
        if (optionalUser.isEmpty()) {
            return null;
        }
        BankUser bankUser = optionalUser.get();
        bankUser.setPassword(request.getPassword());
        return userRepository.save(bankUser);
    }

    @Override
    public Iterable<BankUser> getUsers() {
        return userRepository.findAll();
    }

    @Override
    public BankUser getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public BankUser getUserByUsername(String username) {
        return userRepository.findOneByUsername(username).orElse(null);
    }

    @Override
    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public void deleteAllUsers() {
        userRepository.deleteAll();
    }
}
