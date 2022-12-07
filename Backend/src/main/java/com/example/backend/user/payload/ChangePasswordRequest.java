package com.example.backend.user.payload;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
public class ChangePasswordRequest extends AuthRequest {
    private String oldPassword;
    @Size(min = 5)
    private String newPassword;

    public ChangePasswordRequest(String token, String oldPassword, String newPassword) {
        super(token);
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
    }
}
