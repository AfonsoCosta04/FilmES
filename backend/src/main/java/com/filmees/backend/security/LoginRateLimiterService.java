package com.filmees.backend.security;

import io.github.bucket4j.*;
import org.springframework.stereotype.Service;
import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class LoginRateLimiterService {

    private final Map<String, LoginEntry> attempts = new ConcurrentHashMap<>();

    public boolean isBlocked(String ip) {
        LoginEntry entry = attempts.computeIfAbsent(ip, k -> new LoginEntry());
        boolean allowed = entry.bucket.tryConsume(1);

        if (!allowed) {
            entry.incrementBlockLevel();
        }

        return !allowed;
    }

    public void reset(String ip) {
        attempts.remove(ip);
    }

    private static class LoginEntry {
        private int bloqueios = 0;
        private Bucket bucket;

        public LoginEntry() {
            this.bucket = criarBucket(0);
        }

        private Bucket criarBucket(int bloqueios) {
            long minutosBloqueio = (long) Math.pow(2, bloqueios); // 1, 2, 4, 8...
            Refill refill = Refill.greedy(5, Duration.ofMinutes(minutosBloqueio));
            Bandwidth limit = Bandwidth.classic(5, refill);
            return Bucket4j.builder().addLimit(limit).build();
        }

        public void incrementBlockLevel() {
            bloqueios++;
            this.bucket = criarBucket(bloqueios);
        }
    }
}
