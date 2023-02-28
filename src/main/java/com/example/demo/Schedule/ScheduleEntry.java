package com.example.demo.Schedule;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.example.demo.Course.Course;
import com.example.demo.professor.Professor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter @Setter @NoArgsConstructor @EqualsAndHashCode
@Entity
@Table(name="schedule_entry")
public class ScheduleEntry {
	@Id
	@SequenceGenerator(name = "scheduleEntry_sequence", sequenceName = "scheduleEntry_sequence", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "scheduleEntry_sequence")
	private Long id;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="professor_id")
	private Professor professor;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="course_id")
	private Course course;
	
	private String roomType;
	private Integer roomNumber;
	private String department;
	//private String building;
	public ScheduleEntry(Long id, Professor professor, Course course, String roomType, Integer roomNumber,String department) {
		this.id = id;
		this.professor = professor;
		this.course = course;
		this.roomType = roomType;
		this.roomNumber = roomNumber;
		this.department = department;
	}
	
	
	
	
	
}
