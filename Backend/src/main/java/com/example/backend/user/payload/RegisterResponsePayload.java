package com.example.backend.user.payload;

public class RegisterResponsePayload extends LoginResponsePayload {
    public RegisterResponsePayload(String token, UserResponsePayload user) {
        super(token, user);
    }
}
