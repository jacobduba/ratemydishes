package com.example.backend.user;

import com.example.backend.user.exceptions.InvalidPayloadException;
import com.example.backend.user.payload.*;
import io.swagger.annotations.Example;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.LinkedHashMap;

@RestController
@RequestMapping("user")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "(Jacob) Given valid user creds, returns auth token and user miscellaneous information.",
            responses = {@ApiResponse(
                    responseCode = "200",
                    description = "User information and token.")})
    @PostMapping(value = "login", produces = MediaType.APPLICATION_JSON_VALUE)
    public LoginResponsePayload login(@RequestBody LoginRequestPayload loginRequestPayload) {
        return new LoginResponsePayload(
                userService.loginGenerateJwtToken(loginRequestPayload),
                userService.getUserResponsePayload(loginRequestPayload.getNetId())
        );
    }

    @PostMapping("ping")
    public String pingPost(@RequestBody AuthRequestPayload authRequestPayload) {
        userService.getUserFromAuthPayload(authRequestPayload);
        return "pong";
    }

    @Operation(summary = "(Jacob) Given valid netId and password, returns new user token and miscellaneous new user information.",
            responses = {@ApiResponse(
                    responseCode = "200",
                    description = "User information and token.")})
    @PostMapping("register")
    public RegisterResponsePayload register(@Valid @RequestBody RegisterRequestPayload registerRequestPayload, BindingResult br) {
        if (br.hasErrors()) throw new InvalidPayloadException();
        userService.registerUser(registerRequestPayload);

        return new RegisterResponsePayload(
                userService.registrationGenerateJwtToken(registerRequestPayload),
                userService.getUserResponsePayload(registerRequestPayload.getNetId())
        );
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
