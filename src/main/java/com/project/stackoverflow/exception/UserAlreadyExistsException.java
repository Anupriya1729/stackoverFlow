package com.project.stackoverflow.exception;

public class UserAlreadyExistsException extends Exception{
    public UserAlreadyExistsException(String message){ super(message);};
}