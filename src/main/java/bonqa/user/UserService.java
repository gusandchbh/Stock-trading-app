package bonqa.user;

import bonqa.user.exception.UserNotFoundException;
import bonqa.user.request.UpdateEmailRequest;
import bonqa.user.request.UpdatePasswordRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final Logger logger = LoggerFactory.getLogger(UserService.class);

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

        logger.info("Retrieved all users");
        return userDTOs;
    }

    public void updateEmail(@Valid UpdateEmailRequest updateEmailRequest, Long id) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            String newEmail = updateEmailRequest.getEmail();
            user.setEmail(newEmail);
            userRepository.save(user);
            logger.info("Updated email for user with id: {}", id);
        } else {
            logger.error("Error updating email for user with id: {}", id);
            throw new UserNotFoundException("User not found");
        }
    }

    public void deleteUserById(Long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) {
            logger.error("Error deleting user with id: {}", id);
            throw new UserNotFoundException("No user found.");
        }
        userRepository.deleteById(id);
        logger.info("Deleted user with id: {}", id);
    }

    public void updatePassword(@Valid UpdatePasswordRequest updatePasswordRequest, Long id) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            String newPassword = updatePasswordRequest.getPassword();
            user.setPassword(passwordEncoder.encode(newPassword));
            userRepository.save(user);
            logger.info("Updated password for user with id: {}", id);
        } else {
            logger.error("Error updating password for user with id: {}", id);
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
            logger.info("Retrieved user with id: {}", id);
            return Optional.of(userDTO);
        }
        logger.error("Error retrieving user with id: {}", id);
        return Optional.empty();
    }
}
