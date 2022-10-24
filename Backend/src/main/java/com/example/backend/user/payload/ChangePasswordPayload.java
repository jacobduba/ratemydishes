package com.example.backend.user.payload;

import javax.validation.constraints.Size;

public class ChangePasswordPayload extends AuthRequestPayload {
    private String oldPassword;

    @Size(min=5)
    private String newPassword;

    public ChangePasswordPayload() {}

    public String getOldPassword() {
        return oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }
}
