package com.example.demo.controller;

import java.util.List;

import javax.validation.Valid;

import org.hibernate.service.spi.InjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Course;
import com.example.demo.service.CourseService;

@RestController
@RequestMapping(path="/api/v1/courses")
public class CourseController {
	
	private final CourseService courseServ;
	@Autowired
	public CourseController(CourseService courseServ) {
		this.courseServ = courseServ;
	}
	//works and tested
	@PostMapping
	public ResponseEntity<String> createNewCourse(@Valid @RequestBody Course c) {
		courseServ.createCourse(c);
		return new ResponseEntity<>("Course created successfully",HttpStatus.CREATED);
	}
	//works
	
	@GetMapping
	public List<Course> getCourses(@RequestParam(required=false) Integer semester){
		//TODO get by semester
		System.out.println(semester);
		if(semester==null) {
			return courseServ.getCourses();
		}else {
			return courseServ.getCoursesBySemester(semester);
		}
		
	}
	
	@DeleteMapping(path="/{courseId}")
	public ResponseEntity<String> deleteCourseWithId(@PathVariable("courseId") Long courseID) throws Exception{
		Course c = courseServ.deleteCourseById(courseID);
		return new ResponseEntity<>("Course with id " + courseID + " was deleted",HttpStatus.OK);
	}
	
	
	@PutMapping(path="/{courseId}")
	public ResponseEntity<String> editCourseDetails(@PathVariable("courseId") Long courseID,@Valid @RequestBody Course c) throws Exception{
		Course course = courseServ.updateCourseDetails(c, courseID);
		return new ResponseEntity<>("Course details updated successfully",HttpStatus.OK);
	}
	//TODO delete and put mappings
	
}
