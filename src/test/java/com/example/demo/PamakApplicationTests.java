package com.example.demo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.c;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;

import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.example.demo.controller.ProfessorController;
import com.example.demo.model.Professor;
import com.example.demo.service.ProfessorService;
import com.fasterxml.jackson.databind.ObjectMapper;
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class PamakApplicationTests {

	
	@LocalServerPort
	private int port;
	
	@Autowired
	private ProfessorController controller;
	@MockBean
	private ProfessorService profService;
	@Autowired
	private MockMvc mockMvc;
	
	@Test
	void contextLoads() {
		assertThat(controller).isNotNull();
	}
	
	
	
	@Test 
	void whenPostProfessor_thenProfessorInDB() throws Exception {
		Professor prof1 = new Professor(1L,"Themis","Tabouris","eimaitabouris@gmail.com");
		Professor prof2 = new Professor(1L+1,"Kwstas","Vergidis","yoVergidhs@gmail.com");
		List<Professor> profs = List.of(prof1,prof2);
		//given(profService.getProfessors()).willReturn(profs);
        final ObjectMapper mapper = new ObjectMapper();
        final String jsonContent = mapper.writeValueAsString(prof1);
		this.mockMvc.perform(post("http://localhost:" + port + "/api/v1/professors")
		.contentType(MediaType.APPLICATION_JSON)
		.content(jsonContent))
		
		
		.andExpect(content().string(containsString("professor added")));
	}
	//types of tests: related to the returning http response

}
