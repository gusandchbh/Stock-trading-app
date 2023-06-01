package bonqa.authentication.jwt;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class TokenCleanupScheduler {

    private final TokenRepository tokenRepository;

    public TokenCleanupScheduler(TokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    @Scheduled(cron = "0 0 0 * * ?")
    public void cleanupExpiredAndRevokedTokens() {
        tokenRepository.deleteExpiredOrRevokedTokens();
    }
}
