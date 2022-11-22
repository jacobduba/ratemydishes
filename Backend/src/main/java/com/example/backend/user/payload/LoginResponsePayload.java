package com.example.backend.user.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class LoginResponsePayload {
    public String token;
    public UserResponsePayload user;
}
