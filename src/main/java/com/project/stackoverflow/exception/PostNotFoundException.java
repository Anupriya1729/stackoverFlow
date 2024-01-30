package com.project.stackoverflow.exception;

public class PostNotFoundException extends RuntimeException {

    public PostNotFoundException() {
        super();
    }

    public PostNotFoundException(String message) {
        super(message);
    }
}
