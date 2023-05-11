package bonqa.user;

import bonqa.authentication.request.RegisterRequest;
import bonqa.portfolio.Portfolio;
import java.math.BigDecimal;
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
    user.setRole(Role.USER);

    // Create a new portfolio for the user
    Portfolio portfolio = new Portfolio();
    portfolio.setAccountBalance(BigDecimal.valueOf(100000));
    portfolio.setUser(user);

    // Associate the portfolio with the user
    user.setPortfolio(portfolio);

    return user;
  }

}
