package com.softserve.itacademy.exception;

public class TaskNotFoundException extends RuntimeException {

    public TaskNotFoundException() {
    }

    public TaskNotFoundException(String message) {
        super(message);
    }

}
