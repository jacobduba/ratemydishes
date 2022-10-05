package com.example.backend.user;

import org.springframework.stereotype.Component;

public class LoginPayload {
    private String netId;
    private String password;

    public LoginPayload(String netId, String password) {
        this.netId = netId;
        this.password = password;
    }

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
