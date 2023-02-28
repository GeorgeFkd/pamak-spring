package com.example.demo.config;

import com.example.demo.professor.ProfessorRepository;
import com.example.demo.professor.ProfessorService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProfessorConfig {

	@Bean
	CommandLineRunner commandLineRunner(ProfessorRepository repository) {
		return args -> {
			
//			Professor prof1 = new Professor(1L,"Themis","Tabouris","eimaitabouris@gmail.com");
//			Professor prof2 = new Professor(1L+1,"Kwstas","Vergidis","yoVergidhs@gmail.com");
//			
//			repository.saveAll(List.of(prof1,prof2));
			
		};
	}
}
