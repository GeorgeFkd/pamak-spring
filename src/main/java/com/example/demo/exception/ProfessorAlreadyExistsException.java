package com.example.demo.exception;

import com.example.demo.model.Professor;

import lombok.Getter;

@Getter
public class ProfessorAlreadyExistsException extends Exception {
	
	
	//status code 302 or 303
	private Professor existingProfessor;
	private String message;
	public ProfessorAlreadyExistsException(String msg,Professor p) {
		super();
		this.message = msg;
		this.existingProfessor = p;
	}

}
