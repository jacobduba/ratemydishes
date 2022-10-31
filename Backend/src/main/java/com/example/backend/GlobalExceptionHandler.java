package com.example.backend;

import com.example.backend.admin.exceptions.UserNotPrivilegedException;
import com.example.backend.user.exceptions.IncorrectUsernameOrPasswordException;
import com.example.backend.user.exceptions.InvalidPayloadException;
import com.example.backend.user.exceptions.InvalidTokenException;
import com.example.backend.user.exceptions.UserAlreadyExistsException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiError> httpNotReadableException(HttpMessageNotReadableException ex) {
        return ApiError.buildRes(
                HttpStatus.UNAUTHORIZED,
                "No payload sent.",
                ex
        );
    }

    @ExceptionHandler(InvalidPayloadException.class)
    public ResponseEntity<ApiError> invalidPayloadException(InvalidPayloadException ex) {
        return ApiError.buildRes(
                HttpStatus.UNAUTHORIZED,
                "Payload does not contain expected properties.",
                ex
        );
    }

    @ExceptionHandler(IncorrectUsernameOrPasswordException.class)
    public ResponseEntity<ApiError> handleException(IncorrectUsernameOrPasswordException ex) {
        return ApiError.buildRes(
                HttpStatus.UNAUTHORIZED,
                "Incorrect username or password",
                ex
        );
    }

    @ExceptionHandler(InvalidTokenException.class)
    public ResponseEntity<ApiError> invalidTokenException(InvalidTokenException ex) {
        return ApiError.buildRes(
                HttpStatus.UNAUTHORIZED,
                "Token is invalid.",
                ex
        );
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ApiError> userAlreadyExistsException(UserAlreadyExistsException ex) {
        return ApiError.buildRes(
                HttpStatus.UNAUTHORIZED,
                "User already exists.",
                ex
        );
    }

    @ExceptionHandler(UserNotPrivilegedException.class)
    public ResponseEntity<ApiError> userNotPrivelgedException(UserNotPrivilegedException ex) {
        return ApiError.buildRes(
                HttpStatus.UNAUTHORIZED,
                "User is not privileged enough to view this data.",
                ex
        );
    }
}
