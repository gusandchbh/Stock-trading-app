package com.bonqa.bonqa.domain.user;

import com.bonqa.bonqa.domain.model.User;
import com.bonqa.bonqa.domain.model.data.request.UpdateEmailRequest;
import com.bonqa.bonqa.domain.model.data.request.UpdatePasswordRequest;
import com.bonqa.bonqa.domain.repository.UserRepository;
import com.bonqa.bonqa.dto.UserDTO;
import com.bonqa.bonqa.exception.UserNotFoundException;
import jakarta.validation.Valid;
import java.util.ArrayList;
import java.util.List;
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

  public List<UserDTO> getAllUsers() {
    List<User> users = userRepository.findAll();
    List<UserDTO> userDTOs = new ArrayList<>();

    for (User user : users) {
      UserDTO userDTO = new UserDTO();
      userDTO.setId(user.getId());
      userDTO.setEmail(user.getEmail());
      userDTO.setUsername(user.getUsername());
      userDTO.setCreatedTime(user.getCreatedTime());
      userDTO.setRole(user.getRole());
      userDTOs.add(userDTO);
    }

    return userDTOs;
  }

  public void updateEmail(@Valid UpdateEmailRequest updateEmailRequest, Long id) {
    Optional<User> userOptional = userRepository.findById(id);
    if (userOptional.isPresent()) {
      User user = userOptional.get();
      user.preventUnauthorizedUpdate();
      String newEmail = updateEmailRequest.getEmail();
      user.setEmail(newEmail);
      userRepository.save(user);
    } else {
      throw new UserNotFoundException("User not found");
    }
  }

  public void updatePassword(@Valid UpdatePasswordRequest updatePasswordRequest, Long id) {
    Optional<User> userOptional = userRepository.findById(id);
    if (userOptional.isPresent()) {
      User user = userOptional.get();
      user.preventUnauthorizedUpdate();
      String newEmail = updatePasswordRequest.getPassword();
      user.setPassword(passwordEncoder.encode(newEmail));
      userRepository.save(user);
    } else {
      throw new UserNotFoundException("User not found");
    }
  }

  public Optional<UserDTO> getUser(Long id) {
    Optional<User> user = userRepository.findById(id);
    if (user.isPresent()) {
      UserDTO userDTO = new UserDTO();
      userDTO.setId(user.get().getId());
      userDTO.setUsername(user.get().getUsername());
      userDTO.setEmail(user.get().getEmail());
      userDTO.setCreatedTime(user.get().getCreatedTime());
      userDTO.setRole(user.get().getRole());
      return Optional.of(userDTO);
    }
    return Optional.empty();
  }

}
