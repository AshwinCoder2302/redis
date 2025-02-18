package com.codewithachu.redis.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HelloService {

    private final RateLimiterService rateLimiterService;

    public String fetchGreeting() {
        long remainingRequests = rateLimiterService.getRemainingRequests();

        if (remainingRequests <= 0) {
            long timeLeft = rateLimiterService.getTimeUntilReset();
            return "API Limit Exceeded. Try again in " + timeLeft + " seconds.";
        }
        return "Hello, Aliens! You have " + remainingRequests + " requests left.";
    }
}
