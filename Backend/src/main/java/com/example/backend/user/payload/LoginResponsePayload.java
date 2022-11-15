package com.example.backend.user.payload;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class LoginResponsePayload {
    @ApiModelProperty(example = "mock_token")
    public String token;
    public UserResponsePayload user;
}
