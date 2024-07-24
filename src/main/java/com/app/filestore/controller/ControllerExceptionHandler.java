package com.app.filestore.controller;

import com.app.filestore.dto.response.ErrorDtoResponse;
import com.app.filestore.dto.response.ValidationErrorDtoResponse;
import com.app.filestore.exception.file.FileNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(FileNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDtoResponse handleFileNotFoundException(FileNotFoundException ex) {
        return new ErrorDtoResponse(ex.getMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDtoResponse handleIllegalArgumentException(IllegalArgumentException ex) {
        return new ErrorDtoResponse(ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ValidationErrorDtoResponse handleMethodNotValidException(BindingResult bindingResult) {
        List<String> messages = getMessagesFromFieldErrors(bindingResult.getFieldErrors());
        return new ValidationErrorDtoResponse(messages);
    }
    private List<String> getMessagesFromFieldErrors(List<FieldError> fieldErrors) {
        return fieldErrors.stream()
                .map(fieldError -> fieldError.getField() + " : " + fieldError.getDefaultMessage())
                .toList();
    }
}
