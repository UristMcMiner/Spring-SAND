package de.dhbw_mannheim.sand.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import javax.transaction.Transactional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.core.PrettyPrinter;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import de.dhbw_mannheim.sand.SAND;
import de.dhbw_mannheim.sand.model.Login;
import de.dhbw_mannheim.sand.model.Student;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = SAND.class)
@Transactional
@Rollback(value=true)
@WebAppConfiguration
public class StudentControllerTest {

	@Autowired
	private WebApplicationContext context;

	private MockMvc mvc;

	@Before
	public void setUp() {
		this.mvc = MockMvcBuilders.webAppContextSetup(this.context).build();
	}

	@Test
	public void testGetStudentById() throws Exception {
		Login login = new Login();
		login.setLogin("Rainer_Colgen");
		login.setPassword("Rainer_Colgen");
		ObjectMapper mapper = new ObjectMapper();
		PrettyPrinter printer = new DefaultPrettyPrinter();
		ObjectWriter writer = mapper.writer().with(printer);
		String json = writer.writeValueAsString(login);
		MockHttpServletRequestBuilder postRequest = post("/sand/session/")
				.contentType(MediaType.APPLICATION_JSON)
				.content(json);
		MvcResult result0= mvc.perform(postRequest).andReturn();
		String object = (String) result0.getResponse().getContentAsString();
		System.out.println(object);
		int finish = object.indexOf("user")-3;
		String authorization = object.substring(7, finish);
		System.out.println("AUTH: "+authorization);
		
		MockHttpServletRequestBuilder getRequest = get("/sand/students/9999");
		getRequest.header("authorization", authorization);
<<<<<<< HEAD
		ResultActions result;
		result = mvc.perform(getRequest);
		result.andDo(print());
		result.andExpect(status().isOk());
		result.andExpect(jsonPath("$.login").value("Rainer_Colgen"));
		getRequest = get("/sand/students/9999");
		getRequest.header("authorization", authorization);
		result = mvc.perform(getRequest);
=======
		ResultActions result = mvc.perform(getRequest);
>>>>>>> origin/rest
		result.andExpect(status().isNotFound());
		getRequest = get("/sand/students/5");
		getRequest.header("authorization", authorization);
		result = mvc.perform(getRequest);
		result.andDo(print());
		result.andExpect(status().isOk());
	}        
        
<<<<<<< HEAD
        @Test
=======
    @Test
>>>>>>> origin/rest
	public void testAdd() throws Exception {
		Login login = new Login();
		login.setLogin("Rainer_Colgen");
		login.setPassword("Rainer_Colgen");
		ObjectMapper mapper = new ObjectMapper();
		PrettyPrinter printer = new DefaultPrettyPrinter();
		ObjectWriter writer = mapper.writer().with(printer);
		String json = writer.writeValueAsString(login);
		MockHttpServletRequestBuilder postRequest = post("/sand/session/")
				.contentType(MediaType.APPLICATION_JSON)
				.content(json);
		MvcResult result0= mvc.perform(postRequest).andReturn();
		String object = (String) result0.getResponse().getContentAsString();
		System.out.println(object);
		int finish = object.indexOf("user")-3;
		String authorization = object.substring(7, finish);
		System.out.println("AUTH: "+authorization);
		
		//generate Student JSON
		String content = "{\"type\" : \"student\","
<<<<<<< HEAD
				+ "\"id\" : 2,"
				+ "\"user\" : {\"id\": 2,"
				+ "\"deleted\" : 0,"
				+ "\"roles\" : [ ]},"
=======
				+ "\"user\" : {\"id\": 2,"
				+ "\"deleted\" : 0,"
				+ "\"roles\" : [ ]},"
				+ "\"course\" : {\"id\" : 1},"
>>>>>>> origin/rest
				+ "\"startDate\" : \"1990-08-12\","
				+ "\"endDate\" : \"2998-12-31\","
				+ "\"enrollmentNumber\" : \"66555\"}";
		
		postRequest = post("/sand/students/")
				.contentType(MediaType.APPLICATION_JSON)
				.content(content);
		postRequest.header("authorization", authorization);
		ResultActions result;
		result = mvc.perform(postRequest);
		result.andDo(print());
<<<<<<< HEAD
		result.andExpect(status().isCreated());
		result.andExpect(jsonPath("$.user.login").value("Joachim_Schmidt"));
	}
        @Test
	public void testDelete() throws Exception {
=======
		result.andExpect(status().isOk());
	}
        
    @Test
	public void testEdit() throws Exception {
>>>>>>> origin/rest
		Login login = new Login();
		login.setLogin("Rainer_Colgen");
		login.setPassword("Rainer_Colgen");
		ObjectMapper mapper = new ObjectMapper();
		PrettyPrinter printer = new DefaultPrettyPrinter();
		ObjectWriter writer = mapper.writer().with(printer);
		String json = writer.writeValueAsString(login);
		MockHttpServletRequestBuilder postRequest = post("/sand/session/")
				.contentType(MediaType.APPLICATION_JSON)
				.content(json);
		MvcResult result0= mvc.perform(postRequest).andReturn();
		String object = (String) result0.getResponse().getContentAsString();
		System.out.println(object);
		int finish = object.indexOf("user")-3;
		String authorization = object.substring(7, finish);
		System.out.println("AUTH: "+authorization);
		
		//generate Student JSON
		String content = "{\"type\" : \"student\","
<<<<<<< HEAD
				+ "\"id\" : 2,"
				+ "\"user\" : {\"id\": 2,"
				+ "\"deleted\" : 0,"
				+ "\"roles\" : [ ]},"
=======
				+ "\"user\" : {\"id\": 2,"
				+ "\"deleted\" : 0,"
				+ "\"roles\" : [ ]},"
				+ "\"course\" : {\"id\" : 1},"
>>>>>>> origin/rest
				+ "\"startDate\" : \"1990-08-12\","
				+ "\"endDate\" : \"2998-12-31\","
				+ "\"enrollmentNumber\" : \"66555\"}";
		
		postRequest = post("/sand/students/")
				.contentType(MediaType.APPLICATION_JSON)
				.content(content);
		postRequest.header("authorization", authorization);
		ResultActions result;
		result = mvc.perform(postRequest);
<<<<<<< HEAD
		
		//delete Student
		MockHttpServletRequestBuilder deleteRequest = delete("/sand/students/" + mapper.readValue(result.andReturn().getResponse().getContentAsString(), Student.class).getId())
				.contentType(MediaType.APPLICATION_JSON)
				.content(content);
		deleteRequest.header("authorization", authorization);
		result = mvc.perform(deleteRequest);
		result.andDo(print());
		result.andExpect(status().isOk());
		result.andExpect(content().string("true"));
	}
        
        @Test
	public void testEdit() throws Exception {
=======
		int stuID = mapper.readValue(result.andReturn().getResponse().getContentAsString(), Student.class).getId();
		
		//edit Student JSON
		content = "{\"type\" : \"student\","
				+ "\"id\" : " + stuID + ","
				+ "\"user\" : {\"id\": 2,"
				+ "\"deleted\" : 0,"
				+ "\"roles\" : [ ]},"
				+ "\"course\" : {\"id\" : 1},"
				+ "\"startDate\" : \"1990-08-12\","
				+ "\"endDate\" : \"2998-12-31\","
				+ "\"enrollmentNumber\" : \"66556\"}";
		
		MockHttpServletRequestBuilder putRequest = put("/sand/students/")
				.contentType(MediaType.APPLICATION_JSON)
				.content(content);
		putRequest.header("authorization", authorization);
		result = mvc.perform(putRequest);
		result.andDo(print());
		result.andExpect(status().isOk());
		result.andExpect(jsonPath("$.enrollmentNumber").value("66556"));
		
		//try editing non existing Student
		content = "{\"type\" : \"student\","
				+ "\"id\" : 9999,"
				+ "\"user\" : {\"id\": 9999,"
				+ "\"deleted\" : 0,"
				+ "\"roles\" : [ ]},"
				+ "\"course\" : {\"id\" : 1},"
				+ "\"startDate\" : \"1990-08-12\","
				+ "\"endDate\" : \"2998-12-31\","
				+ "\"enrollmentNumber\" : \"66556\"}";
		
		putRequest = put("/sand/students/")
				.contentType(MediaType.APPLICATION_JSON)
				.content(content);
		putRequest.header("authorization", authorization);
		result = mvc.perform(putRequest);
		result.andDo(print());
		result.andExpect(status().isInternalServerError());
	}
        
    @Test
	public void testDelete() throws Exception {
>>>>>>> origin/rest
		Login login = new Login();
		login.setLogin("Rainer_Colgen");
		login.setPassword("Rainer_Colgen");
		ObjectMapper mapper = new ObjectMapper();
		PrettyPrinter printer = new DefaultPrettyPrinter();
		ObjectWriter writer = mapper.writer().with(printer);
		String json = writer.writeValueAsString(login);
		MockHttpServletRequestBuilder postRequest = post("/sand/session/")
				.contentType(MediaType.APPLICATION_JSON)
				.content(json);
		MvcResult result0= mvc.perform(postRequest).andReturn();
		String object = (String) result0.getResponse().getContentAsString();
		System.out.println(object);
		int finish = object.indexOf("user")-3;
		String authorization = object.substring(7, finish);
		System.out.println("AUTH: "+authorization);
		
		//generate Student JSON
		String content = "{\"type\" : \"student\","
<<<<<<< HEAD
				+ "\"id\" : 2,"
				+ "\"user\" : {\"id\": 2,"
				+ "\"deleted\" : 0,"
				+ "\"roles\" : [ ]},"
=======
				+ "\"user\" : {\"id\": 2,"
				+ "\"deleted\" : 0,"
				+ "\"roles\" : [ ]},"
				+ "\"course\" : {\"id\" : 1},"
>>>>>>> origin/rest
				+ "\"startDate\" : \"1990-08-12\","
				+ "\"endDate\" : \"2998-12-31\","
				+ "\"enrollmentNumber\" : \"66555\"}";
		
		postRequest = post("/sand/students/")
				.contentType(MediaType.APPLICATION_JSON)
				.content(content);
		postRequest.header("authorization", authorization);
		ResultActions result;
		result = mvc.perform(postRequest);
		
<<<<<<< HEAD
		//edit Student JSON
		content = "{\"type\" : \"student\","
				+ "\"id\" : 2,"
				+ "\"user\" : {\"id\": 2,"
				+ "\"deleted\" : 0,"
				+ "\"roles\" : [ ]},"
				+ "\"startDate\" : \"1990-08-12\","
				+ "\"endDate\" : \"2998-12-31\","
				+ "\"enrollmentNumber\" : \"66556\"}";
		
		MockHttpServletRequestBuilder putRequest = put("/sand/students/")
				.contentType(MediaType.APPLICATION_JSON)
				.content(content);
		putRequest.header("authorization", authorization);
		result = mvc.perform(putRequest);
		result.andDo(print());
		result.andExpect(status().isOk());
		result.andExpect(jsonPath("$.enrollmentNumber").value("66556"));
		
		//try editing non existing Student
		content = "{\"type\" : \"student\","
				+ "\"id\" : 9999,"
				+ "\"user\" : {\"id\": 9999,"
				+ "\"deleted\" : 0,"
				+ "\"roles\" : [ ]},"
				+ "\"startDate\" : \"1990-08-12\","
				+ "\"endDate\" : \"2998-12-31\","
				+ "\"enrollmentNumber\" : \"66556\"}";
		
		putRequest = put("/sand/students/")
				.contentType(MediaType.APPLICATION_JSON)
				.content(content);
		putRequest.header("authorization", authorization);
		result = mvc.perform(putRequest);
		result.andDo(print());
		result.andExpect(status().isConflict());
=======
		//delete Student
		MockHttpServletRequestBuilder deleteRequest = delete("/sand/students/" + mapper.readValue(result.andReturn().getResponse().getContentAsString(), Student.class).getId())
				.contentType(MediaType.APPLICATION_JSON)
				.content(content);
		deleteRequest.header("authorization", authorization);
		result = mvc.perform(deleteRequest);
		result.andDo(print());
		result.andExpect(status().isOk());
		result.andExpect(content().string("true"));
>>>>>>> origin/rest
	}
}
