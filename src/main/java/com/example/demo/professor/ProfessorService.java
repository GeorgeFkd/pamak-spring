package com.example.demo.professor;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.Course.Course;
import com.example.demo.Course.CourseRepository;

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
//		Professor prof1 = new Professor(1L,"Themis","Tabouris");
//		return List.of(prof1);
		System.out.println("hello " + professorRepository.findAll());
		return this.professorRepository.findAll();
	}
	
	public void addNewProfessor(Professor p) {
		
		//works
		//business logic for when a professor should not be added 
		Optional<Professor> profOptional = professorRepository.findProfessorByLastName(p.getLastName());
		if(profOptional.isPresent()) {
			System.out.println("This last name already exists in the database are you sure?");
			throw new IllegalStateException("last name already exists in the database");
		}
		professorRepository.save(p);
		System.out.println("youhouuuuu");
	}
	
	public void deleteProfessor(Long profID) {
		boolean profExists = professorRepository.existsById(profID);
		if(!profExists) {
			throw new IllegalStateException("professor with id " + profID + " does not exist");
		}
		
		professorRepository.deleteById(profID);
		
		
		
	}

	@Transactional
	public Professor updateProfessorDetails(Long id, Professor p) throws Exception {
//		boolean profExists = professorRepository.existsById(id);
//		if(!profExists) {
//			System.out.println("Professor with id: " + id + "doesn't exist");
//			throw new IllegalStateException("Professor with details: " + id + " " + firstName + " " + lastName + " doesn't exist");
//		}
		//prosoxh to p den exei id 
		Professor prof = professorRepository.findById(id).orElseThrow(()-> new NotFoundException());
		//TODO --> MONO AN EINAI SYMPLHRWMENA
		if(!(p.getFirstName().isBlank())){
			prof.setFirstName(p.getFirstName());
		}

		if(!(p.getLastName().isBlank())){
			prof.setLastName(p.getLastName());
		}
		if(!(p.getEmail().isBlank())){
			prof.setEmail(p.getEmail());
		}
		professorRepository.save(prof);
		return prof;
		//validation that params exist and are of certain form
		
		
		
	}


	public List<Course> getHisCourses(Long profID) {
		//problem
		if(!professorRepository.existsById(profID)) {
			throw new IllegalStateException("Mf doesn't exist");
		}

		List<Course> courses = courseRepo.findCoursesByProfessorsId(profID);
		
		//System.out.println(courses.get(0).getName());
		
		return courses;
	}

	//works(it needed transactional to operate as the addCourse operation wasn't saving anything
	@Transactional
	public void addCourseToProfessor(Long profID, Long courseID) {
		Optional<Course> c = courseRepo.findById(courseID);
		Optional<Professor> p = professorRepository.findById(profID);
		if(!c.isPresent()) {
			System.out.println("this course doesnt exist. id: " + courseID);
		}else {
			if(!p.isPresent()) {
				System.out.println("Professor doesnt exist");
				
			}else {
				p.get().addCourse(c.get());
				System.out.println("course was added on his list");
				System.out.println(p.get().getCourses());
			}
		}
		
	}


	public Professor getProfessorByID(Long profID) throws NotFoundException {
		// TODO Auto-generated method stub
		Optional<Professor> prof = professorRepository.findById(profID);
		prof.orElseThrow(()-> new NotFoundException());
		
		
		return prof.get();
		
	}


	//<T,ID>

}
