package com.example.demo;

import com.example.demo.professor.ProfessorService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Course.CourseRepository;
import com.example.demo.Schedule.ScheduleEntryRepository;
import com.example.demo.professor.ProfessorRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@SpringBootApplication
@RestController
@EnableAutoConfiguration(exclude = {SecurityAutoConfiguration.class})
public class PamakApplication implements CommandLineRunner {

	private final CourseRepository courseRepo;
	private final ProfessorRepository professorRepo;
	private final ScheduleEntryRepository scheduleEntryRepo;
	
	
	public static void main(String[] args) {
		SpringApplication.run(PamakApplication.class, args);
	}
	
	
	@GetMapping(path="/")
	public String greet() {
		return "hello there";
	}
	
	@Override
	public void run(String... args) {
		
		
//		Course c1 = new Course(1L,3,"Managing Business Processes",false);
//		Course c2 = new Course(1L+1,7,"Ethics and governance",true);
//		Course c3 = new Course(1L+2,6,"Systems Analysis and Design",false);
//		courseRepo.saveAll(List.of(c1,c2,c3));
//		
//
//		Professor prof1 = new Professor(1L,"Themis","Tabouris","eimaitabouris@gmail.com");
//		Professor prof2 = new Professor(1L+1,"Kwstas","Vergidis","yoVergidhs@gmail.com");
//		professorRepo.saveAll(List.of(prof1,prof2));
//		
//		
//		ScheduleEntry s1 = new ScheduleEntry(1L,prof1,c2,"Αίθουσα",12);
//		ScheduleEntry s2 = new ScheduleEntry(1L+1,prof1,c3,"Αίθουσα",4);
//		ScheduleEntry s3 = new ScheduleEntry(1L+2,prof2,c1,"Εργαστήριο",238);
//		ScheduleEntry s4 = new ScheduleEntry(1L+3,prof2,c3,"Εργαστήριο",334);
//		
//		scheduleEntryRepo.saveAll(List.of(s1,s2,s3,s4));
//		
//		
//		scheduleEntryRepo.deleteByProfessorIdAndCourseId(prof1.getId(), c2.getId());
	}

}
