package com.hihusky.auth;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * GreetingController
 */
@RestController
public class GreetingController {
    @GetMapping("/")
    String welcome() {
        return "Welcome to auth";
    }
}