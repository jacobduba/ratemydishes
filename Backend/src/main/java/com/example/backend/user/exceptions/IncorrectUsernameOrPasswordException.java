package com.example.backend.user.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNAUTHORIZED, reason = "Username or password is incorrect.")
public class IncorrectUsernameOrPasswordException extends RuntimeException {
}
