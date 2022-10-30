package com.example.backend.user;

import com.example.backend.ApiError;
import com.example.backend.user.exceptions.IncorrectUsernameOrPasswordException;
import com.example.backend.user.exceptions.InvalidPayloadException;
import com.example.backend.user.exceptions.InvalidTokenException;
import com.example.backend.user.exceptions.UserAlreadyExistsException;
import com.example.backend.user.payload.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.LinkedHashMap;

@RestController @RequestMapping("user")
public class UserController {
    private UserService userService;

    @Autowired
    public UserController(UserService userService/*, ObjectMapper objectMapper*/) {
        this.userService = userService;
    }

    // LinkedHashMap preserves order (https://stackoverflow.com/questions/68993015/how-to-set-the-order-of-json-properties-when-sending-a-map-object)
    @PostMapping("login")
    public LinkedHashMap<String, Object> login(@RequestBody LoginRequestPayload loginRequestPayload) {
        LinkedHashMap<String, Object> res = new LinkedHashMap<>();

        res.put("token", userService.loginGenerateJwtToken(loginRequestPayload));
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
    public LinkedHashMap<String, Object> register(@Valid @RequestBody RegisterRequestPayload registerRequestPayload, BindingResult br) {
        if (br.hasErrors()) throw new InvalidPayloadException();
        userService.registerUser(registerRequestPayload);

        LinkedHashMap<String, Object> res = new LinkedHashMap<>();

        res.put("status", "Registration for user '" + registerRequestPayload.getNetId() + "' successful.");
        res.put("token", userService.registrationGenerateJwtToken(registerRequestPayload));
        res.put("user", userService.getUserResponsePayload(registerRequestPayload.getNetId()));

        return res;
    }

    @PostMapping("delete")
    public LinkedHashMap<String, Object> delete(@RequestBody DeleteRequestPayload deleteRequestPayload) {
        String netId = userService.deleteUser(deleteRequestPayload);

        LinkedHashMap<String, Object> res = new LinkedHashMap<>();

        res.put("status", HttpStatus.ACCEPTED);
        res.put("netId", netId);

        return res;
    }

    @PostMapping("changepw")
    public LinkedHashMap<String, Object> changePassword(@Valid @RequestBody ChangePasswordPayload changePasswordPayload, BindingResult br) {
        if (br.hasErrors()) throw new InvalidPayloadException();
        userService.changeUserPW(changePasswordPayload);

        LinkedHashMap<String, Object> res = new LinkedHashMap<>();
        res.put("status", HttpStatus.ACCEPTED);
        res.put("token", changePasswordPayload.getToken());
        return res;
    }
}
