package com.example.backend.user.payload;

import lombok.Data;

@Data
public class LoginRequestPayload {
    private String netId;
    private String password;

    public LoginRequestPayload() {}

    public boolean isNull() {
        return netId == null || password == null;
    }
}
