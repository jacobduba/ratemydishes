package com.example.backend.user.payload;

import lombok.Data;

@Data
public class AuthRequestPayload {
    private String token;

    public AuthRequestPayload() {}
}
