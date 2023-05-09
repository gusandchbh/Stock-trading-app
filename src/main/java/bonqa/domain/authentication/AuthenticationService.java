package bonqa.domain.authentication;

import bonqa.domain.model.data.request.AuthenticationRequest;
import bonqa.domain.model.data.request.RegisterRequest;
import bonqa.domain.security.token.JwtService;
import bonqa.domain.model.Token;
import bonqa.domain.model.TokenType;
import bonqa.domain.model.User;
import bonqa.domain.repository.TokenRepository;
import bonqa.domain.repository.UserRepository;
import bonqa.domain.user.UserFactory;
import bonqa.exception.BadRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
  private final UserRepository repository;
  private final TokenRepository tokenRepository;
  private final JwtService jwtService;
  private final AuthenticationManager authenticationManager;

  private final UserFactory userFactory;

  private final UserRepository userRepository;

  public String register(RegisterRequest request) {
    if (userRepository.existsByUsername(request.getUsername())) {
      throw new BadRequestException("Username already exists.");
    }
    if (userRepository.existsByEmail(request.getEmail())) {
      throw new BadRequestException("Email already exists.");
    }
    User user = userFactory.createFromRegisterRequest(request);
    var savedUser = repository.save(user);
    var jwtToken = jwtService.generateToken(user);
    saveUserToken(savedUser, jwtToken);
    return jwtToken;
  }

  public String authenticate(AuthenticationRequest request) {
    authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(
            request.getUsername(),
            request.getPassword()
        )
    );
    var user = repository.findByUsername(request.getUsername())
        .orElseThrow();
    var jwtToken = jwtService.generateToken(user);
    revokeAllUserTokens(user);
    saveUserToken(user, jwtToken);
    return jwtToken;
  }

  private void saveUserToken(User user, String jwtToken) {
    var token = Token.builder()
        .user(user)
        .token(jwtToken)
        .tokenType(TokenType.BEARER)
        .expired(false)
        .revoked(false)
        .build();
    tokenRepository.save(token);
  }

  private void revokeAllUserTokens(User user) {
    var validUserTokens = tokenRepository.findAllValidTokenByUser(Math.toIntExact(user.getId()));
    if (validUserTokens.isEmpty()) {
      return;
    }
    validUserTokens.forEach(token -> {
      token.setExpired(true);
      token.setRevoked(true);
    });
    tokenRepository.saveAll(validUserTokens);
  }
}