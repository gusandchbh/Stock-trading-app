package com.bonqa.bonqa.domain.security.token;

import com.bonqa.bonqa.domain.repository.TokenRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class TokenCleanupScheduler {

  private final TokenRepository tokenRepository;

  public TokenCleanupScheduler(TokenRepository tokenRepository) {
    this.tokenRepository = tokenRepository;
  }

  @Scheduled(cron = "0 0 0 * * ?") // Run at midnight every day
  public void cleanupExpiredAndRevokedTokens() {
    tokenRepository.deleteExpiredOrRevokedTokens();
  }
}
