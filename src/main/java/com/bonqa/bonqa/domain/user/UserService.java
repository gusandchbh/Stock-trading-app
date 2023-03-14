package com.bonqa.bonqa.domain.user;

import com.bonqa.bonqa.domain.model.User;
import com.bonqa.bonqa.domain.model.data.request.UpdateEmailRequest;
import com.bonqa.bonqa.domain.model.data.request.UpdateNamesRequest;
import com.bonqa.bonqa.domain.model.data.request.UpdatePasswordRequest;
import com.bonqa.bonqa.domain.repository.UserRepository;
import jakarta.validation.Valid;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  private final PasswordEncoder passwordEncoder;
  private final UserRepository userRepository;

  @Autowired
  public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
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
