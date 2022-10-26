package com.example.demo.exception;

import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;

import lombok.Getter;


@Getter
public class ProfessorNotFoundException extends NotFoundException {

	
	private String msg;

	public ProfessorNotFoundException(String msg) {
		super();
		this.msg = msg;
	}
	
	
}
