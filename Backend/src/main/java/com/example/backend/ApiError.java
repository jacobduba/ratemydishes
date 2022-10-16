package com.example.backend;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

/**
 * This class returns a friendly and safe error while logging debug information.
 */
public class ApiError {
    private HttpStatus status;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private LocalDateTime timestamp;
    private String message;

    Logger logger = LoggerFactory.getLogger(BackendApplication.class);

    public ApiError(HttpStatus status, Throwable ex) {
        timestamp = LocalDateTime.now();
        this.status = status;
        this.message = "Unexpected error";
        logger.warn("Exception: " + ex.getMessage());
    }

    public ApiError(HttpStatus status, String message, Throwable ex) {
        this(status, ex);
        this.message = message;
    }

    public HttpStatus getStatus() {
        return status;
    }
}