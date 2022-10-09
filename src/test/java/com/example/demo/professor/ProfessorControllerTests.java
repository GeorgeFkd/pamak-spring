package com.example.demo.professor;
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
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.example.demo.controller.ProfessorController;
import com.example.demo.model.Professor;
import com.example.demo.repository.ProfessorRepository;
import com.example.demo.service.ProfessorService;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import static org.mockito.BDDMockito.given;
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class ProfessorControllerTests {

	@Autowired
	private MockMvc mockMvc;
	
	@LocalServerPort
	private int port;
	
	@Autowired
	private ProfessorController controller;
	
	@MockBean
	private ProfessorService mockedService;
	
	@MockBean
	private ProfessorRepository profRepoMock;
	
	
	//Tests:getProfessorWithId OK
	@Test
	void canRetrieveProfessorByIDIfExists() throws Exception {
		Professor prof1 = new Professor(1L,"Themis","Tabouris","eimaitabouris@gmail.com");	
		final ObjectMapper mapper = new ObjectMapper();
        final String jsonContent = mapper.writeValueAsString(prof1);
		given(mockedService.getProfessorByID(1L)).willReturn(prof1);
		//when
        System.out.println(jsonContent);
		MockHttpServletResponse response = mockMvc.perform(get("/api/v1/professors/1")
				.accept(MediaType.APPLICATION_JSON))
				.andReturn().getResponse();
		//then
		assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
		assertThat(response.getContentAsString()).isEqualTo(jsonContent);
		System.out.println(jsonContent);
		System.out.println(response.getContentAsString());
	}
	
	//Tests:getProfessorWithId if id not in db OK
	@Test
	void canRetrieveProfessorByIDIfNotExists() throws Exception{
		//thelw na dw oti an den yparxei apantaei swsta
		given(mockedService.getProfessorByID(2L)).willThrow(new NotFoundException());
		
		//when 
		MockHttpServletResponse response = mockMvc.perform(
		get("/api/v1/professors/2").accept(MediaType.APPLICATION_JSON))
		.andReturn().getResponse();
		
		assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
		assertThat(response.getContentAsString().contains("not found")).isTrue();
		
	}
	
	
	//tests:registerNewProfessor request validation OK
	@Test
	void canHandleMissingArgumentsWhenCreatingProfessor() throws Exception{
		Professor p = new Professor(1L,"Themis","","");
		final ObjectMapper mapper = new ObjectMapper();
        final String jsonContent = mapper.writeValueAsString(p);
        
        
		MockHttpServletResponse response = mockMvc.perform(
		post("http://localhost:" + port + "/api/v1/professors")
		.contentType(MediaType.APPLICATION_JSON).content(jsonContent))
		.andReturn().getResponse();
		
		
		String expectedInResponseContent = "validation check";
		assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
		assertThat(response.getContentAsString().contains(expectedInResponseContent)).isTrue();
	}
	
	//tests: editProfessor works but the test doesnt [TODO: THIS FOR INTEGRATION TEST]
	//@Test 
	void canEditProfessorDetails() throws Exception {
		//prepei kapws na ton valoume sthn db
		Professor p = new Professor(1L,"Themis","Tabouris","eimaitabouris@gmail.com");
		
		final ObjectMapper mapper = new ObjectMapper();
				//.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		final ObjectWriter jsonWriter = mapper.writer().withoutAttribute("id");
        final String jsonContent = jsonWriter.writeValueAsString(p);
        p.setFirstName("Efthimis");
        final String jsonContentNew = jsonWriter.writeValueAsString(p);

        
//		given(mockedService.updateProfessorDetails(1L,p))
//		.willReturn(p);
        //given(profRepoMock.findById(1L)).willReturn((Optional.of(p)));
		
        
        //to add prof in db,with JsonIgnore at id 
        MockHttpServletResponse res = mockMvc.perform(
        		post("http://localhost:" + port + "/api/v1/professors")
        		.contentType(MediaType.APPLICATION_JSON).content(jsonContent))
        		.andReturn().getResponse();
        
        System.out.println(jsonContent + "\n" + jsonContentNew);
        
        
		MockHttpServletResponse response = mockMvc.perform(
				put("http://localhost:" + port + "/api/v1/professors/1")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.content(jsonContentNew))
				.andReturn().getResponse();
		//the status is ok the object returned is empty
		assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
		//doesnt return properly the object when given
		System.out.println(response.getContentAsString() + " getContentAsString()");
		assertThat(response.getContentAsString()).isEqualTo(jsonContentNew);
	}
	//tests:registerNewProfessor OK
	@Test
	public void canCreateProfessorWithProperArgs() throws Exception {
		Professor p = new Professor(1L,"Themis","Tabouris","eimaitabouris@gmail.com");
		
		final ObjectMapper mapper = new ObjectMapper();
        final String jsonContent = mapper.writeValueAsString(p);
		
		
		MockHttpServletResponse res = mockMvc.perform(
        		post("http://localhost:" + port + "/api/v1/professors")
        		.contentType(MediaType.APPLICATION_JSON).content(jsonContent))
        		.andReturn().getResponse();
		assertThat(res.getStatus()).isEqualTo(HttpStatus.CREATED.value());
		assertThat(res.getContentAsString().contains("professor added to database")).isTrue();
	}
	
	//TODO: FOR INTEGRATION TEST
	//@Test
	public void canDeleteProfessor() {
		
	}
	
	//@Test
	public void canHandleInvalidArgumentsForCreateProfessor() {
		
	}
	
	//@Test
	public void canHandleInvalidArgumentsForCreateCourse() {
		
	}
	
	
	
}
