package com.project.stackoverflow.exception;

public class MultipleTagsFoundException extends RuntimeException{
    public MultipleTagsFoundException(String message){
        super(message);
    }
}
