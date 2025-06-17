package com.interview.employeeapplication.exception;

public class ResourceConflictException extends RuntimeException {
    public ResourceConflictException(String msg) {
        super(msg);
    }
}
