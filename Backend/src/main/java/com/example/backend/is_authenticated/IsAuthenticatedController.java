package com.example.backend.is_authenticated;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// This controller allows me to test is a user is authenticated.
@RestController @RequestMapping("isAuthenticated")
public class IsAuthenticatedController {

    @GetMapping("ping")
    public String ping() {
        return "pong";
    }
}
