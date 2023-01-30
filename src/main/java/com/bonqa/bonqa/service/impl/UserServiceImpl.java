package com.bonqa.bonqa.service.impl;

import com.bonqa.bonqa.model.*;
import com.bonqa.bonqa.repository.AccountRepository;
import com.bonqa.bonqa.repository.PortfolioRepository;
import com.bonqa.bonqa.repository.UserRepository;
import com.bonqa.bonqa.requests.LoginRequest;
import com.bonqa.bonqa.requests.RegisterRequest;
import com.bonqa.bonqa.requests.UpdateUserRequest;
import com.bonqa.bonqa.service.AccountService;
import com.bonqa.bonqa.service.CustomerService;
import com.bonqa.bonqa.service.PortfolioService;
import com.bonqa.bonqa.service.UserService;
import com.bonqa.bonqa.utility.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final TokenService tokenService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final CustomerService customerService;

    private final PortfolioService portfolioService;

    private final AccountService accountService;
    private final PortfolioRepository portfolioRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, TokenService tokenService, AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder,
                           CustomerService customerService,
                           PortfolioService portfolioService,
                           AccountService accountService,
                           PortfolioRepository portfolioRepository) {
        this.userRepository = userRepository;
        this.tokenService = tokenService;
        this.customerService = customerService;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.accountService = accountService;
        this.portfolioService = portfolioService;
        this.portfolioRepository = portfolioRepository;
    }

    public String loginUser(LoginRequest userLogin) throws AuthenticationException {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userLogin.username(), userLogin.password()));
        return tokenService.generateToken(authentication);
    }

    public User registerUser(RegisterRequest registerRequest) throws AuthenticationException {
        User user = createUser(registerRequest);
        Customer customer = customerService.createCustomer(user);
        Portfolio portfolio = portfolioService.createPortfolio(customer);
        Account account = accountService.createAccount(customer);
        customer.setPortfolio(portfolio);
        customer.setAccount(account);
        customerService.saveCustomer(customer);
        portfolioService.savePortfolio(portfolio);
        accountService.saveAccount(account);
        return user;
    }

    public User createUser(RegisterRequest registerRequest){
        User user = new User();
        user.setUsername(registerRequest.getUsername());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setEmail(registerRequest.getEmail());
        user.setRole(Role.USER);
        userRepository.save(user);
        return user;
    }

    @Override
    public User updateUser(UpdateUserRequest request, Long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isEmpty()) {
            return null;
        }
        User user = optionalUser.get();
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public Iterable<User> getUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public User getUserByUsername(String username) {
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
