package com.example.demo.course;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import java.util.Optional;

import static org.mockito.BDDMockito.given;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeAll;
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
import org.springframework.test.context.event.annotation.BeforeTestClass;
import org.springframework.test.web.servlet.MockMvc;

import com.example.demo.controller.CourseController;
import com.example.demo.controller.ProfessorController;
import com.example.demo.model.Course;
import com.example.demo.repository.CourseRepository;
import com.example.demo.repository.ProfessorRepository;
import com.example.demo.service.CourseService;
import com.example.demo.service.ProfessorService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class CourseControllerTests {

	@Autowired
	private MockMvc mockMvc;
	
	@LocalServerPort
	private int port;
	
	@Autowired
	private CourseController controller;
	
	@MockBean
	private CourseService courseServiceMock;
	
	@MockBean
	private CourseRepository courseRepoMock;
	
	private static ObjectMapper mapper;
	private static String route;
	
	@BeforeAll
	public static void setup() {
		mapper = new ObjectMapper();
		route = "/api/v1/courses";
	}
	
	@Test
	public void canCreateNewCourseWithProperArgs() throws Exception {
		Course c = new Course(1L,3,"Java Programming",false);
		mapper = new ObjectMapper();
		final String jsonContent = mapper.writeValueAsString(c);
		
		MockHttpServletResponse response = mockMvc.perform
		(post(route)
		.contentType(MediaType.APPLICATION_JSON)
		.content(jsonContent))
		.andReturn().getResponse();
		
		final String expectedMsg = "course created successfully"; 
		
		System.out.println(response.getContentAsString().toLowerCase());
		assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
		assertThat(response.getContentAsString().toLowerCase()).isEqualTo(expectedMsg);
		
	}	
	
	@Test
	public void canHandleCreateNewCourseWithInvalidArgs() throws Exception{
		Course c1 = new Course(1L,0,"Java Programming",false);
		Course c2 = new Course(2L,3,"Java Programmingoamigo",false);
		//System.out.println(c2.getElective());
		JSONObject json1 = new JSONObject(mapper.writeValueAsString(c1));
		
		JSONObject json2 = new JSONObject(mapper.writeValueAsString(c2));
		json2.remove("name");
		
		MockHttpServletResponse response1 = mockMvc.perform
				(post(route)
				.contentType(MediaType.APPLICATION_JSON)
				.content(json1.toString()))
				.andReturn().getResponse();
		MockHttpServletResponse response2 = mockMvc.perform
				(post(route)
				.contentType(MediaType.APPLICATION_JSON)
				.content(json2.toString()))
				.andReturn().getResponse();
		
		String expectedInMsg = "validation check";
		
		//invalid property value(could repeat with everything but not needed in this use case
		assertThat(response1.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
		assertThat(response1.getContentAsString().contains(expectedInMsg)).isTrue();
		
		//property missing
		assertThat(response2.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
		assertThat(response2.getContentAsString().contains(expectedInMsg)).isTrue();
		
		
	}
	
	@Test
	public void canDeleteCourseThatExists() throws Exception {
		Course c1 = new Course(1L,3,"java programming",false);
		
//		given(courseRepoMock.existsById(1L)).willReturn(false);
//		given(courseRepoMock.findById(1L)).willReturn(Optional.of(c1));
		
		MockHttpServletResponse res = mockMvc.perform(
		delete(route + "/{id}",1)).andReturn().getResponse();
		
		String expectedInMsg = "was deleted";
		
		assertThat(res.getStatus()).isEqualTo(HttpStatus.OK.value());
		assertThat(res.getContentAsString().contains(expectedInMsg)).isTrue();
		
				
		
	}
	
	
	@Test
	public void throwsExceptionWhenDeleteCourseNotExists() throws Exception{
		
		given(courseServiceMock.deleteCourseById(1L)).willThrow(NotFoundException.class);
		
		MockHttpServletResponse res = mockMvc.perform(
				delete(route + "/{id}",1L)).andReturn().getResponse();
		
		
		String expectedInMsg = "not found";
		assertThat(res.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
		assertThat(res.getContentAsString().contains(expectedInMsg)).isTrue();
	}
	
	@Test
	public void canEditCourseDetailsWhenCourseExists() throws Exception{
		Course c1 = new Course(1L,3,"java programming",false);
		given(courseRepoMock.findById(1L)).willReturn(Optional.of(c1));
		
		
		
		c1.setName("Introduction to OOP");
		c1.setSemester(4);
		String json = mapper.writeValueAsString(c1);
		
		MockHttpServletResponse res = mockMvc.perform(
				put(route + "/{id}",1L).contentType(MediaType.APPLICATION_JSON).content(json)).andReturn().getResponse();
		
		String expectedInMsg = "updated successfully";
		assertThat(res.getStatus()).isEqualTo(HttpStatus.OK.value());
		assertThat(res.getContentAsString().contains(expectedInMsg)).isTrue();
		
		
	}
	
	
	//not yet for some reason
	@Test 
	public void canEditCourseDetailsWhenCourseNotExists() throws Exception{
		Course c1 = new Course(1L,3,"java programming",false);
		c1.setName("Introduction to OOP");
		c1.setSemester(4);
		String json = mapper.writeValueAsString(c1);
		
		//given(courseServiceMock.updateCourseDetails(c1,1L)).willThrow(new NotFoundException());
		
		MockHttpServletResponse res = mockMvc.perform(
				put(route + "/{id}",1L)
				.contentType(MediaType.APPLICATION_JSON)
				.content(json))
				.andReturn().getResponse();
		
		String expectedInMsg = "not found";
		assertThat(res.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
		assertThat(res.getContentAsString().contains(expectedInMsg)).isTrue();
	}
		
}
