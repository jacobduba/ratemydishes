package com.example.backend.user.payload;

import lombok.Data;

import javax.validation.constraints.Size;

@Data
public class ChangePasswordPayload extends AuthRequestPayload {
    private String oldPassword;

    @Size(min=5)
    private String newPassword;

    public ChangePasswordPayload() {}
}
