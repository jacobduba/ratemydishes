package com.example.backend.user.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter @AllArgsConstructor
public class DeleteRequest extends AuthRequest {
    // We want the user to confirm that they have the right password.
    private String password;
}