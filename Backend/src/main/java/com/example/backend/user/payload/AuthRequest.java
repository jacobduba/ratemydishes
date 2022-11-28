package com.example.backend.user.payload;

import lombok.Data;

@Data
public class AuthRequest {
    private String token;

    public AuthRequest() {}
}
