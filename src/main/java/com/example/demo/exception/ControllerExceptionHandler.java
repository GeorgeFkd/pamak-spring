package com.example.demo.exception;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerExceptionHandler {

	
	@ExceptionHandler(value=NotFoundException.class)
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	public String professorWasNotFoundException(NotFoundException e){
		return "Professor was not found hello";
	} 
	
	
	@ExceptionHandler(value=IllegalArgumentException.class)
	@ResponseStatus(value=HttpStatus.BAD_REQUEST)
	public String notAllArgumentsForProfessorRegister(IllegalArgumentException e){
		return "You haven't provided args: " + e.getMessage();
	}
	
	
	
	@ExceptionHandler(value=MethodArgumentNotValidException.class)
	@ResponseStatus(value=HttpStatus.BAD_REQUEST)
	public String handleValidationException(MethodArgumentNotValidException e) {
		return "An argument didn't pass the validation check " + e.getMessage();
	}
	
	
}
