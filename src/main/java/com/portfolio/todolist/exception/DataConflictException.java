package com.portfolio.todolist.exception;

public class DataConflictException extends RuntimeException {

    public DataConflictException(String message) {
        super(message);
    }
}