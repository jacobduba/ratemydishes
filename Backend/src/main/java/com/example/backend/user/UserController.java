package com.example.backend.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController @RequestMapping("user")
public class UserController {
    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("login")
    public HashMap<String, Object> login(@RequestBody LoginPayload loginDetails) {
        HashMap<String, Object> ret = new HashMap<>();
        userService.validateLoginPayload(loginDetails);
        User user = userService.getUser(loginDetails.netId);
        ret.put("user", user.getId());
        return ret;
    }

    @GetMapping("ping")
    public String ping() {
        return "pong";
    }
}
