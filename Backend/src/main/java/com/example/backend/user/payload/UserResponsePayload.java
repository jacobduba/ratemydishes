package com.example.backend.user.payload;

import com.example.backend.user.User;

/**
 * When returning a user object via api, we don't
 * want to expose certain properties such as
 * the hashed password. So this object takes a
 * user and "filters" out info we don't want to
 * share!
 */
public class UserResponsePayload {
    private String netId;

    public UserResponsePayload(User user) {
        this.netId = user.getNetId();
    }

    public String getNetId() {
        return netId;
    }
}
