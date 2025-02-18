package com.codewithachu.redis.controller;

import com.codewithachu.redis.service.GreetingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class GreetingController {

    private final GreetingService greetingService;

    @GetMapping("/hello")
    public String getGreeting(){
       return greetingService.getGreeting();
    }
}
