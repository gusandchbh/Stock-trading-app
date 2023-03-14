package com.bonqa.bonqa.domain.user;

import com.bonqa.bonqa.domain.model.Role;
import com.bonqa.bonqa.domain.model.User;
import com.bonqa.bonqa.domain.model.data.request.RegisterRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserFactory {
  private final PasswordEncoder passwordEncoder;

  public UserFactory(PasswordEncoder passwordEncoder) {
    this.passwordEncoder = passwordEncoder;
  }

  public User createFromRegisterRequest(RegisterRequest request) {
    User user = new User();
    user.setUsername(request.getUsername());
    user.setPassword(passwordEncoder.encode(request.getPassword()));
    user.setEmail(request.getEmail());
    user.setRole(Role.ROLE_USER);

    return user;
  }

}
