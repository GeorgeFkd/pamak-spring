package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.Service;

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
	
	public void createCourse(Course c) {
		//check to see if it exists
		List<Course> course = courseRepo.findCourseByName(c.getName());
		if(!course.isEmpty()) {
			System.out.println("course with that name already exists");
			throw new IllegalStateException("Cant create a course with the same name");
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
		boolean exists = courseRepo.existsById(courseID);
		System.out.println("hello outside");
		if(exists) {
			Course c = courseRepo.findById(courseID).orElseThrow(()-> new NotFoundException());
			courseRepo.deleteById(courseID);
			return c;
		}else {
			System.out.println("hello inside");
			throw new NotFoundException();
		}
		
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
			throw new NotFoundException();
		}
	}
	
	
}
