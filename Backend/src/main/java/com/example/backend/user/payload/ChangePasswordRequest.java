package com.example.backend.user.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Size;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class ChangePasswordRequest extends AuthRequest {
    private String oldPassword;
    @Size(min=5)
    private String newPassword;
}
