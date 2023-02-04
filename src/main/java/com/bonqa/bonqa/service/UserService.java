package com.bonqa.bonqa.service;

import com.bonqa.bonqa.model.*;
import com.bonqa.bonqa.repository.UserRepository;
import com.bonqa.bonqa.model.requests.LoginRequest;
import com.bonqa.bonqa.model.requests.RegisterRequest;
import com.bonqa.bonqa.model.requests.UpdateUserRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final TokenService tokenService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final PortfolioFactory portfolioFactory;
    private final UserFactory userFactory;

    @Autowired
    public UserService(
            UserRepository userRepository,
            TokenService tokenService,
            AuthenticationManager authenticationManager,
            PasswordEncoder passwordEncoder,
            PortfolioFactory portfolioFactory,
            UserFactory userFactory
    ) {
        this.userRepository = userRepository;
        this.tokenService = tokenService;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.userFactory = userFactory;
        this.portfolioFactory = portfolioFactory;
    }

    public String loginUser(LoginRequest userLogin) throws AuthenticationException {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userLogin.username(), userLogin.password()));

        return tokenService.generateToken(authentication);
    }

    public User registerUser(RegisterRequest registerRequest) throws AuthenticationException {
        User user = userFactory.createFromRegisterRequest(registerRequest);
        Portfolio portfolio = portfolioFactory.createPortfolio(user);
        portfolio.setUser(user);
        userRepository.save(user);

        return user;
    }

    public User createUser(RegisterRequest registerRequest){
        User user = userFactory.createFromRegisterRequest(registerRequest);

        return user;
    }

    public User updateUser(UpdateUserRequest request, Long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isEmpty()) {
            return null;
        }
        User user = optionalUser.get();
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        return userRepository.save(user);
    }

    // all methods below are available through userRepository (.findAll, .findById, .findBy('username', id number), .deleteById, deleteAll
    public Iterable<User> getUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public User getUserByUsername(String username) {
        return userRepository.findOneByUsername(username).orElse(null);
    }

    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
    }

    public void deleteAllUsers() {
        userRepository.deleteAll();
    }
}
