package com.example.backend.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController @RequestMapping("user")
public class UserController {
    private UserRepository userRepository;

    @Autowired
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("login")
    public HashMap<String, Object> login(@RequestBody LoginPayload loginDetails) {
        HashMap<String, Object> ret = new HashMap<>();

        return ret;
    }

    @GetMapping("ping")
    public String ping() {
        return "pong";
    }
}
