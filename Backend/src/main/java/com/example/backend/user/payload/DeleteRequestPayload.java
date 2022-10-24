package com.example.backend.user.payload;

import lombok.Data;

@Data
public class DeleteRequestPayload extends AuthRequestPayload {
    // We want the user to confirm that they have the right password.
    private String password;
}