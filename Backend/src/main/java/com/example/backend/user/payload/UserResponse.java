package com.example.backend.user.payload;

import com.example.backend.role.Role;
import com.example.backend.user.User;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

/**
 * When returning a user object via api, we don't
 * want to expose certain properties such as
 * the hashed password. So this object takes a
 * user and "filters" out info we don't want to
 * share!
 * TODO use projections
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {
    private String netId;
    @JsonProperty("isAdmin")
    private boolean isAdmin;

    public UserResponse(User user) {
        netId = user.getNetId();

        isAdmin = false;
        Set<Role> roles = user.getRoles();
        for (Role role : roles) {
            if (role.getName().equals("admin")) {
                isAdmin = true;
                break;
            }
        }
    }
}
