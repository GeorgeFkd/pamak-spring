package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.Service;

import com.example.demo.exception.CourseAlreadyExistsException;
import com.example.demo.exception.CourseNotFoundException;
import com.example.demo.model.Course;
import com.example.demo.repository.CourseRepository;
import com.example.demo.repository.ProfessorRepository;

@Service
public class CourseService {
	private final ProfessorRepository professorRepo;
	private final CourseRepository courseRepo;
	
	@Autowired
	public CourseService(ProfessorRepository professorRepo, CourseRepository courseRepo) {
		this.professorRepo = professorRepo;
		this.courseRepo = courseRepo;
	}
	
	public List<Course> getCourses(){
		return courseRepo.findAll();
	}
	
	public void createCourse(Course c) throws CourseAlreadyExistsException {
		//check to see if it exists
		List<Course> course = courseRepo.findCourseByName(c.getName());
		if(!course.isEmpty()) {
			System.out.println("course with that name already exists");
			throw new CourseAlreadyExistsException("Cant create a course with the same name",course.get(0));
		}else {
			courseRepo.save(c);
		}
	}
	
	public List<Course> getCoursesBySemester(Integer semester){
		List<Course> c = courseRepo.findCourseBySemester(semester);
		return c;
	}
	
	public Course deleteCourseById(Long courseID) throws Exception {
		//check if it is in database
		
		Course c = courseRepo.findById(courseID).orElseThrow(()-> new CourseNotFoundException("Course with id " + courseID + "doesn't exist"));
		courseRepo.deleteById(courseID);
		return c;
		
	}
	@Transactional
	public Course updateCourseDetails(Course c,Long courseID) throws Exception {
		Optional<Course> courseOptional = courseRepo.findById(courseID);
		if(courseOptional.isPresent()) {
			Course course = courseOptional.get();
			course.setElective(c.getElective());
			course.setName(c.getName());
			course.setSemester(c.getSemester());
			//courseRepo.save(course);
			return course;
			//course.setProfessors(c.getProfessors());
		}else {
			throw new CourseNotFoundException("Course with ID " + courseID + " doesn't exist");
		}
	}
	
	
}
