package com.example.demo.exception;

import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;

public class CourseNotFoundException extends NotFoundException {

	private String message;
	public CourseNotFoundException(String msg) {
		super();
		this.message =msg;
	}
	
}
