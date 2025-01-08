package pbs.edu.cooperative.config;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class RevokedTokenService {
    private final Map<String, Instant> revokedTokens = new ConcurrentHashMap<>();

    public void revokeToken(String token, Instant expiration) {
        revokedTokens.put(token, expiration);
    }

    public boolean isTokenRevoked(String token) {
        return revokedTokens.containsKey(token);
    }

    @Scheduled(fixedRate = 3600000) // Co godzinę
    public void cleanupExpiredTokens() {
        Instant now = Instant.now();
        revokedTokens.entrySet().removeIf(entry -> entry.getValue().isBefore(now));
    }
}