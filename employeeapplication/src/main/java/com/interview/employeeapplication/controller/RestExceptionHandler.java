package com.interview.employeeapplication.controller;

import com.interview.employeeapplication.dto.ApiError;
import com.interview.employeeapplication.exception.ResourceConflictException;
import com.interview.employeeapplication.exception.ResourceNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@RestControllerAdvice
public class RestExceptionHandler {

    private ResponseEntity<ApiError> buildError(HttpStatus status, String message, String path) {
        var body = new ApiError(
                status.value(),
                status.getReasonPhrase(),
                message,
                LocalDateTime.now(),
                path
        );
        return ResponseEntity.status(status).body(body);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiError> handleNotFound(ResourceNotFoundException ex, HttpServletRequest req) {
        return buildError(HttpStatus.NOT_FOUND, ex.getMessage(), req.getRequestURI());
    }

    @ExceptionHandler(ResourceConflictException.class)
    public ResponseEntity<ApiError> handleConflict(ResourceConflictException ex, HttpServletRequest req) {
        return buildError(HttpStatus.CONFLICT, ex.getMessage(), req.getRequestURI());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleValidation(MethodArgumentNotValidException ex, HttpServletRequest req) {
        String msg = ex.getFieldErrors().stream()
                .map(fe -> fe.getField() + " " + fe.getDefaultMessage())
                .collect(Collectors.joining("; "));
        return buildError(HttpStatus.BAD_REQUEST, msg, req.getRequestURI());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleAll(Exception ex, HttpServletRequest req) {
        return buildError(HttpStatus.INTERNAL_SERVER_ERROR, "Unexpected error", req.getRequestURI());
    }
}
