package com.codewithachu.redis.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class RateLimitService {

    private final RedisTemplate<String, String> redisTemplate;

    public boolean isAllowed() {
        String key = "RATE_LIMIT";
        Long requests = redisTemplate.opsForValue().increment(key, 1);

        if (requests == 1) {
            redisTemplate.expire(key, 1, TimeUnit.MINUTES);
        }
        return requests <= 5;
    }
}
