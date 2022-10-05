package com.example.backend.user;

import org.springframework.stereotype.Component;

public class LoginPayload {
    protected String netId;
    protected String password;

    public LoginPayload(String netId, String password) {
        this.netId = netId;
        this.password = password;
    }

    public boolean isNull() {
        return netId != null && password != null;
    }
}
