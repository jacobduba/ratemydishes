package com.example.backend.user.payload;

public class AuthRequestPayload {
    private String token;

    public AuthRequestPayload() {}

    public String getToken() {
        return token;
    }

    public String toString() {
        return "AuthRequestPayload[token: " + token + "]";
    }
}
