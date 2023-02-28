package com.example.demo.professor;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Course.Course;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;


@RestController
@RequestMapping(path="/api/v1/professors")
public class ProfessorController {

	private final ProfessorService professorService;
	
	@Autowired
	public ProfessorController(ProfessorService professorService) {
		this.professorService = professorService;
	}
	
	
	//http://localhost:8080/api/v1/professors/1
	@ApiOperation(value="Get a professor by id",notes="Returns a professor with the provided id")
	@ApiResponses(value= {
			@ApiResponse(code=200,message = "Professor succesfully retrieved"),
			@ApiResponse(code=404,message = "Professor was not found")
	})
	@GetMapping(path= "/{profId}")
	public ResponseEntity<Professor> getProfessorWithId(@PathVariable("profId") @ApiParam(name="id",value=" Professor id",example="1")Long profID) throws NotFoundException{
		Professor p = professorService.getProfessorByID(profID);
		return new ResponseEntity<Professor>(p,HttpStatus.OK);
		
	}
	//http://localhost:8080/api/v1/professors/1?firstName=giannhs&lastName=magkas&email=tsimph@gmail.com
	@PutMapping(path="/{profID}")
	public ResponseEntity<Professor> editProfessor(@RequestBody Professor p,@PathVariable("profID") Long profID) throws Exception {
		System.out.println(profID);
		Professor prof = professorService.updateProfessorDetails(profID,p);
		System.out.println(prof + " hello");
		return new ResponseEntity<Professor>(prof,HttpStatus.OK);
	}
	
	//http://localhost:8080/api/v1/professors
	@GetMapping()
	public List<Professor> getProfessors(){
		List<Professor> profs = professorService.getProfessors();
		return profs;
	}
	
	//validates the email semi properly but the first name no
	//http://localhost:8080/api/v1/professors {"firstName":"giwrgos", "lastName":"refanidhs","email:"refaG@gmail.com"}
	@PostMapping()
	public ResponseEntity<String> registerNewProfessor(@Valid @RequestBody @ApiParam(name="Professor", value="Professor JSON without courses and id",example="p") Professor p) {
		System.out.println("all good");
		professorService.addNewProfessor(p);
		return new ResponseEntity<>(p + "\n professor added to database",HttpStatus.CREATED);
	}
	//http://localhost:8080/api/v1/professors/1 
	@DeleteMapping(path="/{profId}")
	public String deleteProfessor(@PathVariable("profId") Long profID) {
		professorService.deleteProfessor(profID);
		return "Professor was deleted";
		
	}
	
	//not yet
	//http://localhost:8080/api/v1/professors/1/courses/1
	@PostMapping(path="/{profId}/courses/{courseId}")
	public String addCourseToProfessor(@PathVariable("profId") Long profID,@PathVariable("courseId") Long courseID) {
		professorService.addCourseToProfessor(profID,courseID);
		return "done";
		
	}
	//not yet
	//http://localhost:8080/api/v1/professors/1/courses
	@GetMapping(path="/{profId}/courses")
	public List<Course> getCoursesOfProfessor(@PathVariable("profId") Long profID){
		List<Course> hisCourses = professorService.getHisCourses(profID);
		return hisCourses;
	}
}
