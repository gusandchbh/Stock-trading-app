package com.bonqa.bonqa.domain.user;

import com.bonqa.bonqa.domain.model.Portfolio;
import com.bonqa.bonqa.domain.model.Role;
import com.bonqa.bonqa.domain.model.User;
import com.bonqa.bonqa.domain.repository.UserRepository;
import com.bonqa.bonqa.domain.model.data.request.LoginRequest;
import com.bonqa.bonqa.domain.model.data.request.RegisterRequest;
import com.bonqa.bonqa.domain.model.data.request.UpdateUserRequest;
import com.bonqa.bonqa.domain.security.TokenGenerator;
import com.bonqa.bonqa.exception.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final TokenGenerator tokenGenerator;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final Role.PortfolioFactory portfolioFactory;
    private final UserFactory userFactory;

    @Autowired
    public UserService(
            UserRepository userRepository,
            TokenGenerator tokenGenerator,
            AuthenticationManager authenticationManager,
            PasswordEncoder passwordEncoder,
            Role.PortfolioFactory portfolioFactory,
            UserFactory userFactory
    ) {
        this.userRepository = userRepository;
        this.tokenGenerator = tokenGenerator;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.userFactory = userFactory;
        this.portfolioFactory = portfolioFactory;
    }

    public String loginUser(LoginRequest userLogin) throws AuthenticationException {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userLogin.username(), userLogin.password()));

        return tokenGenerator.generateToken(authentication);
    }

    public User registerUser(RegisterRequest registerRequest) throws AuthenticationException {
        if (userRepository.existsByUsername(registerRequest.getUsername())){
            throw new BadRequestException("Username already exists.");
        }
        if (userRepository.existsByEmail(registerRequest.getEmail())){
            throw new BadRequestException("Email already exists.");
        }
        User user = userFactory.createFromRegisterRequest(registerRequest);
        Portfolio portfolio = portfolioFactory.createPortfolio(user);
        portfolio.setUser(user);
        userRepository.save(user);

        return user;
    }

    public void updateUser(UpdateUserRequest request, Long id) {
        userRepository.findById(id).ifPresentOrElse(user -> {
            user.setFirstName(request.getFirstName());
            user.setLastName(request.getLastName());
            user.setEmail(request.getEmail());
            user.setPassword(passwordEncoder.encode(request.getPassword()));
        }, () -> {
            throw new RuntimeException("User not found");
        });
    }
}
