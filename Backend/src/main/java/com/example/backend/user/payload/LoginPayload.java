package com.example.backend.user.payload;

import org.springframework.stereotype.Component;

public class LoginPayload {
    private String netId;
    private String password;

    public LoginPayload() {}

    public boolean isNull() {
        return netId == null || password == null;
    }

    public String getNetId() {
        return netId;
    }

    public String getPassword() {
        return password;
    }
}
