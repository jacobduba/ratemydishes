package com.example.backend;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

/**
 * This class returns a friendly and safe error while logging debug information.
 */
@Getter
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
        logger.error("Application error in: [" + ex.getClass().getName() + "]", ex);
    }

    public ApiError(HttpStatus status, String message, Throwable ex) {
        this(status, ex);
        this.message = message;
    }

    public static ResponseEntity<ApiError> buildRes(HttpStatus status, String message, Exception ex) {
        ApiError err = new ApiError(
                status,
                message,
                ex
        );
        return new ResponseEntity<>(err, err.getStatus());
    }
}