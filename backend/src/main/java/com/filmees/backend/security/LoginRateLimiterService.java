package com.filmees.backend.security;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Bucket4j;
import io.github.bucket4j.Refill;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class LoginRateLimiterService {

    private final Map<String, LoginEntry> attempts = new ConcurrentHashMap<>();

    public boolean isBlocked(String key) {
        LoginEntry entry = attempts.computeIfAbsent(key, k -> new LoginEntry());
        boolean allowed = entry.bucket.tryConsume(1);

        if (!allowed && !entry.isAlreadyBlocked()) {
            // Substituir por novo com mais bloqueio
            LoginEntry newEntry = entry.nextLevel();
            attempts.put(key, newEntry);
        }

        return !allowed;
    }

    public void reset(String key) {
        attempts.remove(key);
    }

    private static class LoginEntry {
        private final int bloqueios;
        private final Bucket bucket;

        public LoginEntry() {
            this(0);
        }

        public LoginEntry(int bloqueios) {
            this.bloqueios = bloqueios;
            this.bucket = criarBucket(bloqueios);
        }

        private Bucket criarBucket(int bloqueios) {
            long minutosBloqueio = (long) Math.pow(2, bloqueios);
            Refill refill = Refill.greedy(5, Duration.ofMinutes(minutosBloqueio));
            Bandwidth limit = Bandwidth.classic(5, refill);
            return Bucket4j.builder().addLimit(limit).build();
        }

        public LoginEntry nextLevel() {
            return new LoginEntry(this.bloqueios + 1);
        }

        public boolean isAlreadyBlocked() {
            return bucket.getAvailableTokens() == 0;
        }
    }
}
