package com.example.demo.config;

import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.demo.repository.ScheduleEntryRepository;

@Configuration
public class ScheduleEntryConfig {

	@Bean
	CommandLineRunner commandLineRunner3(ScheduleEntryRepository repo) {
		return args -> {
//			ScheduleEntry s1 = new ScheduleEntry();
//			ScheduleEntry s2 = new ScheduleEntry();
//			ScheduleEntry s3 = new ScheduleEntry();
//			ScheduleEntry s4 = new ScheduleEntry();
//			
//			repo.saveAll(List.of(s1,s2,s3,s4));
		};
	}
}
