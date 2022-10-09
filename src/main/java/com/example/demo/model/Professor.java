package com.example.demo.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Getter @Setter @NoArgsConstructor @ToString
@Entity
@Table(name = "professors")
public class Professor {

	@Id
	@SequenceGenerator(name = "professor_sequence", sequenceName = "professor_sequence", allocationSize = 1

	)

	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "professor_sequence")
	//@ApiModelProperty(notes="Professor ID",example="1",required=false)
	private Long id;
	@ApiModelProperty(notes="Professor's First Name",example="Giannhs",required=true)
	@Size(min=3,max=15)
	private String firstName;
	@ApiModelProperty(notes="Professor's last name", example="Refanidis",required=true)
	@Size(min=3,max=15)
	private String lastName;
	@ApiModelProperty(notes="Professor's email for contact with students",example="refaG@gmail.com",required=true)
	@Email(message="Email is not valid")
	private String email;
	@ManyToMany(fetch=FetchType.LAZY,cascade= {CascadeType.PERSIST,CascadeType.MERGE})
	@JoinTable(name="teaching_schedule",joinColumns= {@JoinColumn(name="professor_id")}
									   ,inverseJoinColumns = {@JoinColumn(name="course_id")})
	@JsonIgnore
	private Set<Course> courses = new HashSet<>();
	

	// +courses

	public Professor(Long id, String firstName, String lastName,String email) {
		
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		
		
	}
	
	public void addCourse(Course c) {
		this.courses.add(c);
		c.getProfessors().add(this);
	}
	
	public void removeCourse(Long courseID) {
		Course c = this.courses.stream()
				.filter(course->course.getId() == courseID)
				.findFirst()
				.orElse(null);
		if(c!=null) {
			this.courses.remove(c);
			c.getProfessors().remove(this);
		}else {
			System.out.println("This courseID is not indexed in DB");
		}
	}


}
