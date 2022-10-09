package com.example.demo.professor;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//import org.springframework.data.rest.core.config.Projection;

//@Projection(name="schedule",types= {Professor.class,Course.class})
@Getter @Setter @NoArgsConstructor
public class ScheduleProjection {
	private String firstName;
	private String lastName;
	private String courseName;
	private String courseSemester;
	public ScheduleProjection(String firstName, String lastName, String courseName, String courseSemester) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.courseName = courseName;
		this.courseSemester = courseSemester;
	}
	
	
	
	
}
