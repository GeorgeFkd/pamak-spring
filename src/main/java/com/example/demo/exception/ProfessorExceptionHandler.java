package com.example.demo.exception;

import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ProfessorExceptionHandler {
    
    
    //TODO TEST THOSE
    
    //OK
    @ExceptionHandler(value=ProfessorNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public String professorWasNotFoundException(ProfessorNotFoundException e){
	return e.getMsg();
    }
    
    //OK
    @ExceptionHandler(value=ProfessorAlreadyExistsException.class)
    @ResponseStatus(value= HttpStatus.CONFLICT)
    public String professorAlreadyExists(ProfessorAlreadyExistsException e) {
	return e.getMessage() + "\n Professor: " + e.getExistingProfessor();
    }
    
}
