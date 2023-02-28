package com.example.demo.Course;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.example.demo.professor.Professor;
import org.hibernate.validator.constraints.Range;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter @Setter @NoArgsConstructor
@Entity
@Table(name="courses")
public class Course {

	
	@Id
	@SequenceGenerator(name = "course_sequence", sequenceName = "course_sequence", allocationSize = 1)

	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "course_sequence")
	private Long id;
	@Range(min=1,max=10,message="subject must have a semester of 1-10")
	private Integer semester;
	@NotBlank(message="Name is required")
	private String name;
	@NotNull(message="Need to know if course is elective or not")
	private Boolean elective;
	//required = false
	//Professor
	@ManyToMany(fetch=FetchType.LAZY,cascade= {CascadeType.PERSIST,CascadeType.MERGE},mappedBy="courses")
//	@JsonIgnore
	private Set<Professor> professors = new HashSet<>();
	public Course(Long id, Integer semester, String name,boolean elective) {
	
		this.id = id;
		this.semester = semester;
		this.name = name;
		this.elective = elective;
	}
	
	
}
