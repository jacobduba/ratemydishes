package com.example.backend.user.payload;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
public class RegisterRequestPayload {
    @ApiModelProperty(example = "user1")
    @Pattern(regexp = "^[a-z0-9]{3,8}$") // net ids are 3 to 8 lowercase letters and numbers
    private String netId;
    @ApiModelProperty(example = "testtest")
    @Size(min=5)
    private String password;
}
