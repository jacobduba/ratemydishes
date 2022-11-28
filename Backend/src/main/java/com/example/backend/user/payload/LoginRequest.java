package com.example.backend.user.payload;

import lombok.Data;

@Data
public class LoginRequest {
    private String netId;
    private String password;

    public LoginRequest() {}

    public boolean isNull() {
        return netId == null || password == null;
    }
}
