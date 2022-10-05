package com.example.backend.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController @RequestMapping("user")
public class UserController {
    private UserService userService;
    private ObjectMapper objectMapper;

    @Autowired
    public UserController(UserService userService, ObjectMapper objectMapper) {
        this.userService = userService;
        this.objectMapper = objectMapper;
    }

    @PostMapping("login")
    public ObjectNode login(@RequestBody LoginPayload loginPayload) {
        String loginJwt = userService.generateJwtToken(loginPayload);

        ObjectNode jwtPayload = objectMapper.createObjectNode();
        jwtPayload.put("token", loginJwt);

        // Thought about just adding a user object, but that contains hashed password
        User user = userService.getUser(loginPayload.getNetId());
        ObjectNode userInfo = objectMapper.createObjectNode();
        /* So we will cherry pick what info to set back. Right now that's just the netId,
        but in the future there might be info such as name. */
        userInfo.put("netId", user.getNetId());
        jwtPayload.set("user", userInfo);
        return jwtPayload;
    }

    @GetMapping("ping")
    public String ping() {
        return "pong";
    }
}
