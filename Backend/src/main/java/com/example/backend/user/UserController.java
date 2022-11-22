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
    public LoginResponse login(@RequestBody LoginRequest loginRequest) {
        return new LoginResponse(
                userService.loginGenerateJwtToken(loginRequest),
                userService.getUserResponsePayload(loginRequest.getNetId())
        );
    }

    @Operation(summary = "(Jacob) Returns 'pong' is the token is valid.")
    @PostMapping("ping")
    public String pingPost(@RequestBody AuthRequest authRequestPayload) {
        userService.getUserFromAuthPayload(authRequestPayload);
        return "pong";
    }

    @Operation(summary = "(Jacob) Given valid netId and password, returns new user token and miscellaneous new user information.")
    @PostMapping("register")
    public RegisterResponse register(@Valid @RequestBody RegisterRequest registerRequest, BindingResult br) {
        if (br.hasErrors()) throw new InvalidPayloadException();
        userService.registerUser(registerRequest);

        return new RegisterResponse(
                userService.registrationGenerateJwtToken(registerRequest),
                userService.getUserResponsePayload(registerRequest.getNetId())
        );
    }

    @Operation(summary = "(Jacob) Given valid token and user password, delete account")
    @PostMapping("delete")
    public UserResponse delete(@RequestBody DeleteRequest deleteRequest) {
        User user = userService.deleteUser(deleteRequest);

        return new UserResponse(user);
    }

    @Operation(summary = "(Jacob) Given valid token, current password, and new password, change User's password")
    @PostMapping("changepw")
    public ChangePasswordResponse changePassword(@Valid @RequestBody ChangePasswordRequest changePasswordRequestPayload, BindingResult br) {
        if (br.hasErrors()) throw new InvalidPayloadException();
        userService.changeUserPW(changePasswordRequestPayload);

        return new ChangePasswordResponse(HttpStatus.ACCEPTED, changePasswordRequestPayload.getToken());
    }
}
