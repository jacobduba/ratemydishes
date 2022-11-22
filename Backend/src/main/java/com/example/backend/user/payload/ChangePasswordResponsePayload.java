package com.example.backend.user.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter @Setter @AllArgsConstructor
public class ChangePasswordResponsePayload {
    private HttpStatus status;
    private String token;
}
