package com.project.stackoverflow.exception;

import com.project.stackoverflow.dto.ErrorResponseDTO;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponseDTO> handleUserNotFoundException(UserNotFoundException ex) {
        return new ResponseEntity<>(new ErrorResponseDTO("User not found with userId: "+ ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(PostNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponseDTO> handlePostNotFoundException(PostNotFoundException ex) {
        return new ResponseEntity<>(new ErrorResponseDTO("Post not found with postId: "+ ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ParentQuestionNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponseDTO> handleParentQuestionNotFoundException(ParentQuestionNotFoundException ex) {
        return new ResponseEntity<>(new ErrorResponseDTO("Question not found with id:  " + ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MultipleTagsFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponseDTO> handleMultipleTagsFoundException(MultipleTagsFoundException ex) {
        return new ResponseEntity<>(new ErrorResponseDTO("Multiple tags found with name: "+ ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ErrorResponseDTO> handleException(Exception ex) {
        return new ResponseEntity<>(new ErrorResponseDTO("An unexpected error occurred. "+ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ErrorResponseDTO> handleUserAlreadyExistsException(UserAlreadyExistsException e) {
        return new ResponseEntity<>(new ErrorResponseDTO("The email, phone number or username is already in use."), HttpStatus.BAD_REQUEST);
    }
}

