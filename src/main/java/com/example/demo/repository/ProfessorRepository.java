package com.example.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Professor;


//<T,ID>
@Repository
public interface ProfessorRepository extends JpaRepository<Professor,Long>{

	
	//SELECT * FROM professors WHERE last_name=?;
	
	@Query("SELECT p FROM Professor p WHERE p.lastName = ?1")
	Optional<Professor> findProfessorByLastName(String lastname);
	
	//not tested yet
	List<Professor> findProfessorsByCoursesId(Long courseId);
}
