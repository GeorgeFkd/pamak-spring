package com.example.demo.config;

import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.demo.repository.ProfessorRepository;

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
