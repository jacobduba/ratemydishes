package com.example.backend.user.payload;

import com.example.backend.role.Role;
import com.example.backend.user.User;
import lombok.Data;
import lombok.Getter;

import java.util.Set;

/**
 * When returning a user object via api, we don't
 * want to expose certain properties such as
 * the hashed password. So this object takes a
 * user and "filters" out info we don't want to
 * share!
 */
@Data
public class UserResponsePayload {
    private String netId;
    private boolean isAdmin;

    public UserResponsePayload(User user) {
        netId = user.getNetId();

        isAdmin = false;
        Set<Role> roles = user.getRoles();
        for (Role role : roles) {
            if (role.getName().equals("admin")) {
                isAdmin = true;
            }
        }
    }
}
