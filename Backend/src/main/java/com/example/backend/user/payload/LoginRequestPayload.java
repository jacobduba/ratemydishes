package com.example.backend.user.payload;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class LoginRequestPayload {
    @ApiModelProperty(notes = "name", example = "admin")
    private String netId;
    @ApiModelProperty(example = "admin")
    private String password;

    public LoginRequestPayload() {}

    public boolean isNull() {
        return netId == null || password == null;
    }
}
