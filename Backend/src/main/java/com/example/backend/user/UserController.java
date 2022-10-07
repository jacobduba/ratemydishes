package com.example.backend.user;

import com.example.backend.user.exceptions.InvalidPayloadException;
import com.example.backend.user.payload.AuthRequestPayload;
import com.example.backend.user.payload.LoginRequestPayload;
import com.example.backend.user.payload.RegisterRequestPayload;
import com.example.backend.user.payload.UserResponsePayload;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.LinkedHashMap;

@RestController @RequestMapping("user")
public class UserController {
    private UserService userService;
//    private ObjectMapper objectMapper;

    @Autowired
    public UserController(UserService userService/*, ObjectMapper objectMapper*/) {
        this.userService = userService;
//        this.objectMapper = objectMapper;
    }

    // LinkedHashMap preserves order (https://stackoverflow.com/questions/68993015/how-to-set-the-order-of-json-properties-when-sending-a-map-object)
    @PostMapping("login")
    public LinkedHashMap<String, Object> login(@RequestBody LoginRequestPayload loginRequestPayload) {
        String loginJwt = userService.generateJwtToken(loginRequestPayload);

        LinkedHashMap<String, Object> res = new LinkedHashMap<>();
        res.put("token", loginJwt);
        // See UserResponsePayload to understand what is going on here
        res.put("user", userService.getUserResponsePayload(loginRequestPayload.getNetId()));

        return res;
    }

    @PostMapping("ping")
    public String pingPost(@RequestBody AuthRequestPayload authRequestPayload) {
        User user = userService.getUserFromAuthPayload(authRequestPayload);
        return "pong";
    }

    @PostMapping("register")
    public ObjectNode register(@Valid @RequestBody RegisterRequestPayload registerRequestPayload, BindingResult br) {
        if (br.hasErrors()) throw new InvalidPayloadException();
        userService.registerUser(registerRequestPayload);
        return null;
    }
}
