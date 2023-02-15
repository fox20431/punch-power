package com.hihusky.blog.controllers;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
public class GreetingController {
    @GetMapping("/")
    String welcome() {
        return "Welcome to blog!";
    }
}
