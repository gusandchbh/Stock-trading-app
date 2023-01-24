package com.bonqa.bonqa.service.impl;

import ch.qos.logback.core.testUtil.RandomUtil;
import com.bonqa.bonqa.model.*;
import com.bonqa.bonqa.repository.AccountRepository;
import com.bonqa.bonqa.repository.CustomerRepository;
import com.bonqa.bonqa.repository.PortfolioRepository;
import com.bonqa.bonqa.repository.UserRepository;
import com.bonqa.bonqa.requests.LoginRequest;
import com.bonqa.bonqa.requests.RegisterRequest;
import com.bonqa.bonqa.requests.UpdateUserRequest;
import com.bonqa.bonqa.service.UserService;
import com.bonqa.bonqa.utility.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final TokenService tokenService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final CustomerRepository customerRepository;
    private final PortfolioRepository portfolioRepository;
    private final AccountRepository accountRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, TokenService tokenService, AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder,
                           CustomerRepository customerRepository,
                           PortfolioRepository portfolioRepository,
                           AccountRepository accountRepository) {
        this.userRepository = userRepository;
        this.tokenService = tokenService;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.customerRepository = customerRepository;
        this.portfolioRepository = portfolioRepository;
        this.accountRepository = accountRepository;
    }

    public String loginUser(LoginRequest userLogin) throws AuthenticationException {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userLogin.username(), userLogin.password()));
        return tokenService.generateToken(authentication);
    }

    public User registerUser(RegisterRequest registerRequest) throws AuthenticationException {
        User user = new User();
        user.setUsername(registerRequest.getUsername());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setEmail(registerRequest.getEmail());
        user.setRole(Role.USER);
        userRepository.save(user);
        Customer customer = new Customer();
        customer.setUser(user);
        customerRepository.save(customer);
        Portfolio portfolio = new Portfolio();
        portfolio.setCustomer(customer);
        portfolio.setStockList(new ArrayList<>());
        portfolio.setTotalValue(BigDecimal.valueOf(0));
        portfolioRepository.save(portfolio);
        Account account = new Account();
        account.setAccountNumber((long) RandomUtil.getPositiveInt());
        account.setCustomer(customer);
        account.setBalance(BigDecimal.valueOf(0));
        account.setTransactionList(new ArrayList<>());
        accountRepository.save(account);
        return user;
    }

    @Override
    public User updateUser(UpdateUserRequest request, Long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isEmpty()) {
            return null;
        }
        User user = optionalUser.get();
        user.setPassword(request.getPassword());
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
