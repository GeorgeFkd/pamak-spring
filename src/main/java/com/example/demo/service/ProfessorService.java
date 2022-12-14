package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.exception.CourseNotFoundException;
import com.example.demo.exception.ProfessorAlreadyExistsException;
import com.example.demo.exception.ProfessorNotFoundException;
import com.example.demo.model.Course;
import com.example.demo.model.Professor;
import com.example.demo.repository.CourseRepository;
import com.example.demo.repository.ProfessorRepository;

@Service
public class ProfessorService {
	
	private final ProfessorRepository professorRepository;
	private final CourseRepository courseRepo;
	@Autowired
	public ProfessorService(ProfessorRepository professorRepository,CourseRepository courseRepo) {
		this.professorRepository = professorRepository;
		this.courseRepo = courseRepo;
	}
	
	
	public List<Professor> getProfessors(){

		//TODO can this generate errors?
		System.out.println("hello " + professorRepository.findAll());
		return this.professorRepository.findAll();
	}
	
	public void addNewProfessor(Professor p) throws ProfessorAlreadyExistsException {
		
		//works
		//business logic for when a professor should not be added 
		Optional<Professor> profOptional = professorRepository.findProfessorByLastName(p.getLastName());
		if(profOptional.isPresent()) {
			System.out.println("This last name already exists in the database are you sure?");
			throw new ProfessorAlreadyExistsException("last name already exists in the database",profOptional.get());
		}
		professorRepository.save(p);
		System.out.println("youhouuuuu");
	}
	
	public void deleteProfessor(Long profID) throws ProfessorNotFoundException {
		boolean profExists = professorRepository.existsById(profID);
		if(!profExists) {
			throw new ProfessorNotFoundException("professor with id " + profID + " does not exist");
		}
		
		professorRepository.deleteById(profID);
		
		
		
	}
	//transactional has to do with rollbacks and commits not for saving
	@Transactional
	public Professor updateProfessorDetails(Long id, Professor p) throws Exception {
//		boolean profExists = professorRepository.existsById(id);
//		if(!profExists) {
//			System.out.println("Professor with id: " + id + "doesn't exist");
//			throw new IllegalStateException("Professor with details: " + id + " " + firstName + " " + lastName + " doesn't exist");
//		}
		//prosoxh to p den exei id 
		Professor prof = professorRepository.findById(id).orElseThrow(()-> new ProfessorNotFoundException("Professor with id " + id +" doesn't exist"));
		prof.setFirstName(p.getFirstName());
		prof.setLastName(p.getLastName());
		prof.setEmail(p.getEmail());
		System.out.println(prof + "hello bama");
		//this instead of transactional
		//professorRepository.save(prof);
		return prof;		
		
		
	}


	public List<Course> getHisCourses(Long profID) throws ProfessorNotFoundException {
		//problem
		if(!professorRepository.existsById(profID)) {
			throw new ProfessorNotFoundException("Professor with ID" +profID + " doesn't exist");
		}

		//TODO doesn't this generate an error? or just empty list
		List<Course> courses = courseRepo.findCoursesByProfessorsId(profID);
		
		//System.out.println(courses.get(0).getName());
		
		return courses;
	}

	//works(it needed transactional to operate as the addCourse operation wasn't saving anything
	@Transactional
	public void addCourseToProfessor(Long profID, Long courseID) throws Exception {  	
	    Optional<Course> c = courseRepo.findById(courseID);
	    if(!c.isPresent()) {
		throw new CourseNotFoundException("Course with ID " + courseID + " doesn't exist");
	    }
	
	    Optional<Professor> p = professorRepository.findById(profID);
	    if(!p.isPresent()) {
		throw new ProfessorNotFoundException("Professor with ID " + profID + " doesn't exist");
	    }
	
	    p.get().addCourse(c.get());
	    System.out.println("course was added on his list");
	    System.out.println(p.get().getCourses());	
	}


	public Professor getProfessorByID(Long profID) throws NotFoundException {

	    	Optional<Professor> prof = professorRepository.findById(profID);
		prof.orElseThrow(()-> new ProfessorNotFoundException("Professor with ID " + profID + " doesn't exist"));
		return prof.get();	
	}
	

}
