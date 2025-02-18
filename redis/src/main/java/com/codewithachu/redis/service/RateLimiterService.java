package com.codewithachu.redis.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class RateLimiterService {

    private static final String RATE_LIMIT_KEY = "RATE_LIMIT";
    private static final int MAX_REQUESTS = 5;
    private static final long TIME_WINDOW = 60; // in seconds (1 minute)

    private final RedisTemplate<String, String> redisTemplate;

    public long getRemainingRequests() {
        Long currentRequests = redisTemplate.opsForValue().increment(RATE_LIMIT_KEY);

        // If Redis key was just created, set expiry
        if (currentRequests != null && currentRequests == 1) {
            redisTemplate.expire(RATE_LIMIT_KEY, TIME_WINDOW, TimeUnit.SECONDS);
        }

        return (currentRequests != null) ? Math.max(0, MAX_REQUESTS - currentRequests) : MAX_REQUESTS;
    }

    public long getTimeUntilReset() {
        Long timeLeft = redisTemplate.getExpire(RATE_LIMIT_KEY, TimeUnit.SECONDS);

        // Handle cases where Redis returns -1 (no expiration) or -2 (key does not exist)
        return (timeLeft > 0) ? timeLeft : 0;
    }
}
