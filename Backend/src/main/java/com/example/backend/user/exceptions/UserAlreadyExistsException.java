package com.example.backend.user.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNAUTHORIZED, reason = "User already exists")
public class UserAlreadyExistsException extends RuntimeException{
}
