package com.codewithachu.redis.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GreetingService {

    private final RateLimitService rateLimitService;

    public String getGreeting(){
        if(!rateLimitService.isAllowed()){
            return "API Limit Exceeded";
        }
        return "Hello Aliens";
    }
}
