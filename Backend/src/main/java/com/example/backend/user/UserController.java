package com.example.backend.user;

import com.example.backend.user.exceptions.InvalidPayloadException;
import com.example.backend.user.payload.*;
import io.swagger.v3.oas.annotations.Operation;
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

    @Operation(summary = "(Jacob) Given valid user creds, returns auth token and user miscellaneous information.")
    @PostMapping(value = "login", produces = MediaType.APPLICATION_JSON_VALUE)
    public LoginResponsePayload login(@RequestBody LoginRequestPayload loginRequestPayload) {
        return new LoginResponsePayload(
                userService.loginGenerateJwtToken(loginRequestPayload),
                userService.getUserResponsePayload(loginRequestPayload.getNetId())
        );
    }

    @Operation(summary = "(Jacob) Returns 'pong' is the token is valid.")
    @PostMapping("ping")
    public String pingPost(@RequestBody AuthRequestPayload authRequestPayload) {
        userService.getUserFromAuthPayload(authRequestPayload);
        return "pong";
    }

    @Operation(summary = "(Jacob) Given valid netId and password, returns new user token and miscellaneous new user information.")
    @PostMapping("register")
    public RegisterResponsePayload register(@Valid @RequestBody RegisterRequestPayload registerRequestPayload, BindingResult br) {
        if (br.hasErrors()) throw new InvalidPayloadException();
        userService.registerUser(registerRequestPayload);

        return new RegisterResponsePayload(
                userService.registrationGenerateJwtToken(registerRequestPayload),
                userService.getUserResponsePayload(registerRequestPayload.getNetId())
        );
    }

    @Operation(summary = "(Jacob) Given valid token and user password, delete account")
    @PostMapping("delete")
    public UserResponsePayload delete(@RequestBody DeleteRequestPayload deleteRequestPayload) {
        User user = userService.deleteUser(deleteRequestPayload);

        return new UserResponsePayload(user);
    }

    @Operation(summary = "(Jacob) Given valid token, current password, and new password, change User's password")
    @PostMapping("changepw")
    public ChangePasswordResponsePayload changePassword(@Valid @RequestBody ChangePasswordRequestPayload changePasswordRequestPayload, BindingResult br) {
        if (br.hasErrors()) throw new InvalidPayloadException();
        userService.changeUserPW(changePasswordRequestPayload);

        return new ChangePasswordResponsePayload(HttpStatus.ACCEPTED, changePasswordRequestPayload.getToken());
    }
}
