package com.bonqa.bonqa.domain.user;

import com.bonqa.bonqa.domain.model.Portfolio;
import com.bonqa.bonqa.domain.model.Role;
import com.bonqa.bonqa.domain.model.User;
import com.bonqa.bonqa.domain.model.data.request.LoginRequest;
import com.bonqa.bonqa.domain.model.data.request.RegisterRequest;
import com.bonqa.bonqa.domain.model.data.request.UpdateEmailRequest;
import com.bonqa.bonqa.domain.model.data.request.UpdateNamesRequest;
import com.bonqa.bonqa.domain.model.data.request.UpdatePasswordRequest;
import com.bonqa.bonqa.domain.repository.UserRepository;
import com.bonqa.bonqa.domain.security.TokenGenerator;
import com.bonqa.bonqa.exception.BadRequestException;
import jakarta.validation.Valid;
import java.util.Optional;
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
    Authentication authentication = authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(userLogin.username(), userLogin.password()));

    return tokenGenerator.generateToken(authentication);
  }

  public User registerUser(RegisterRequest registerRequest) throws AuthenticationException {
    if (userRepository.existsByUsername(registerRequest.getUsername())) {
      throw new BadRequestException("Username already exists.");
    }
    if (userRepository.existsByEmail(registerRequest.getEmail())) {
      throw new BadRequestException("Email already exists.");
    }
    User user = userFactory.createFromRegisterRequest(registerRequest);
    Portfolio portfolio = portfolioFactory.createPortfolio(user);
    portfolio.setUser(user);
    userRepository.save(user);

    return user;
  }

  public User updateNames(@Valid UpdateNamesRequest request, Long id) {
    User updatedUser = userRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("User not found"));

    if (request.getFirstName() != null) {
      updatedUser.setFirstName(request.getFirstName());
    }
    if (request.getLastName() != null) {
      updatedUser.setLastName(request.getLastName());
    }

    return userRepository.save(updatedUser);
  }

  public void updateEmail(@Valid UpdateEmailRequest updateEmailRequest, Long id) {
    Optional<User> userOptional = userRepository.findById(id);
    if (userOptional.isPresent()) {
      User user = userOptional.get();
      String newEmail = updateEmailRequest.getEmail();
      user.setEmail(newEmail);
      userRepository.save(user);
    } else {
      throw new RuntimeException("User not found");
    }
  }

  public void updatePassword(@Valid UpdatePasswordRequest updatePasswordRequest, Long id) {
    Optional<User> userOptional = userRepository.findById(id);
    if (userOptional.isPresent()) {
      User user = userOptional.get();
      String newEmail = updatePasswordRequest.getPassword();
      user.setPassword(passwordEncoder.encode(newEmail));
      userRepository.save(user);
    } else {
      throw new RuntimeException("User not found");
    }
  }
}
