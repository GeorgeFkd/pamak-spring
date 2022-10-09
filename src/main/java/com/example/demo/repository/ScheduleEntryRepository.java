package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.model.ScheduleEntry;
//this made it work
@Transactional
public interface ScheduleEntryRepository extends JpaRepository<ScheduleEntry, Long> {
	//works with the transactional annotation above it
	List<ScheduleEntry> deleteByProfessorIdAndCourseId(Long profID,Long courseID);
}
