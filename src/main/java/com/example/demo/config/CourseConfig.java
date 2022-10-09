package com.example.demo.config;

import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.demo.repository.CourseRepository;


@Configuration
public class CourseConfig {

	//those typa beans gotta have different names
	@Bean
	CommandLineRunner commandLineRunner2(CourseRepository repository) {
		return args -> {
			
//			Course c1 = new Course(1L,3,"java programming",false);
//			Course c2 = new Course(1L+1,7,"Ethics and governance",true);
//			Course c3 = new Course(1L+2,6,"Epixeirisiakh ereuna",false);
//			repository.saveAll(List.of(c1,c2,c3));
			
		};
	}
}
