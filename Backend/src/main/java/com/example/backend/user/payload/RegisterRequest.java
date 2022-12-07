package com.example.backend.user.payload;

import lombok.*;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    @Pattern(regexp = "^[a-z0-9]{3,8}$") // net ids are 3 to 8 lowercase letters and numbers
    private String netId;
    @Size(min = 5)
    private String password;
}
