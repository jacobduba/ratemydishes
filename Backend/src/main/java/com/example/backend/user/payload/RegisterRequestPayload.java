package com.example.backend.user.payload;

import lombok.Data;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
public class RegisterRequestPayload {
    @Pattern(regexp = "^[a-z0-9]{3,8}$") // net ids are 3 to 8 lowercase letters and numbers
    private String netId;
    @Size(min=5)
    private String password;
}
