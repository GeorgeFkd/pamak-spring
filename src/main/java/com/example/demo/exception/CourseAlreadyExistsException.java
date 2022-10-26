package com.example.demo.exception;

import com.example.demo.model.Course;

import lombok.Getter;

@Getter
public class CourseAlreadyExistsException extends Exception {

	private String message;
	private Course existingCourse;
	
	public CourseAlreadyExistsException(String msg, Course c) {
		this.message = msg;
		this.existingCourse = c;
	}
	
}
