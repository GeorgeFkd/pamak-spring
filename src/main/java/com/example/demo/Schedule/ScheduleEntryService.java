package com.example.demo.Schedule;

import java.util.List;

//import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ScheduleEntryService {
	@Autowired
	private final ScheduleEntryRepository scheduleEntryRepo;
	
	
	
	public ScheduleEntryService(ScheduleEntryRepository scheduleEntryRepo) {
		this.scheduleEntryRepo = scheduleEntryRepo;
	}



	public List<ScheduleEntry> getScheduleEntries(){
		return scheduleEntryRepo.findAll();
		//get professor and course details
	
		
//		schedEntries.stream().forEach(e->{
//		 e.get
//		});
		
	}
	
	public void removeScheduleEntry(Long profId,Long courseId) {
		//TODO if it fails mb try the other way
		
		scheduleEntryRepo.deleteByProfessorIdAndCourseId(profId, courseId);
		
		
	}

	public String addScheduleEntry(ScheduleEntry scheduleEntry){
		scheduleEntryRepo.save(scheduleEntry);
		return "Successful";
	}
	
}
