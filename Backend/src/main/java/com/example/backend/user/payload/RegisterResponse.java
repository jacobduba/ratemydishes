package com.example.backend.user.payload;

public class RegisterResponse extends LoginResponse {
    public RegisterResponse(String token, UserResponse user) {
        super(token, user);
    }
}
