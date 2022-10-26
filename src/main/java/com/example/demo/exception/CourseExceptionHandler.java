package com.example.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CourseExceptionHandler {

    @ExceptionHandler(value=CourseNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public String CourseWasNotFoundException(CourseNotFoundException e){
	return e.getMessage();
    }
    

    @ExceptionHandler(value=CourseAlreadyExistsException.class)
    @ResponseStatus(value= HttpStatus.CONFLICT)
    public String CourseAlreadyExists(CourseAlreadyExistsException e) {
	return e.getMessage() + "\n Course: " + e.getExistingCourse();
    }
    
}
