package com.example.backend.user.payload;

public class LoginRequestPayload {
    private String netId;
    private String password;

    public LoginRequestPayload() {}

    public boolean isNull() {
        return netId == null || password == null;
    }

    public String getNetId() {
        return netId;
    }

    public String getPassword() {
        return password;
    }

    public String toString() {
        return "LoginRequestPayload[netId: " + netId
                + ", password: " + password
                + "]";
    }
}
