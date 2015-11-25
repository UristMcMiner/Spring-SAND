package de.dhbw_mannheim.sand.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import java.util.Random;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.core.PrettyPrinter;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import de.dhbw_mannheim.sand.SAND;
import de.dhbw_mannheim.sand.model.Login;
import de.dhbw_mannheim.sand.model.PasswordChangeRequest;
import de.dhbw_mannheim.sand.model.User;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = SAND.class)
@WebAppConfiguration

public class UserControllerTest {

	@Autowired
	private WebApplicationContext context;

	private MockMvc mvc;

	@Before
	public void setUp() {
		this.mvc = MockMvcBuilders.webAppContextSetup(this.context).build();
	}

	@Test
	public void testGetUserById() throws Exception {
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
		
		MockHttpServletRequestBuilder getRequest = get("/sand/users/1");
		getRequest.header("authorization", authorization);
		ResultActions result;
		result = mvc.perform(getRequest);
		result.andDo(print());
		result.andExpect(status().isOk());
		result.andExpect(jsonPath("$.login").value("Rainer_Colgen"));
		getRequest = get("/sand/users/111");
		getRequest.header("authorization", authorization);
		result = mvc.perform(getRequest);
		result.andExpect(status().isNotFound());
		getRequest = get("/sand/users/5");
		getRequest.header("authorization", authorization);
		result = mvc.perform(getRequest);
		result.andDo(print());
		result.andExpect(status().isOk());
		result.andExpect(jsonPath("$.login").value("Felix_Bayer"));
	}

	@Test
	public void testGetAll() throws Exception {
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
		
		MockHttpServletRequestBuilder getRequest = get("/sand/users/");
		getRequest.header("authorization", authorization);
		ResultActions result;
		result = mvc.perform(getRequest);
		result.andDo(print());
		result.andExpect(status().isOk());
		List<User> userList = mapper.readValue(result.andReturn().getResponse().getContentAsString(), mapper.getTypeFactory().constructCollectionType(List.class, User.class));
		System.out.println("Size of Userlist: " + userList.size());
	}
	
	@Test
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
		
		//generate Random User JSON
		String firstName = randomString(6);
		String lastName = randomString(7);
		String content = "{\"login\" : \"" + firstName + "_" + lastName + "\","
				+ "\"firstname\" : \"" + firstName + "\","
				+ "\"lastname\" : \"" + lastName + "\","
				+ "\"email\" : \"" + firstName + "." + lastName + "@dhbw-mannheim.de\","
				+ "\"deleted\" : 0,"
				+ "\"roles\" : [ ],"
				+ "\"password\" : \"" + firstName + "_" + lastName + "\"}";
		
		MockHttpServletRequestBuilder getRequest = post("/sand/users/")
				.contentType(MediaType.APPLICATION_JSON)
				.content(content);
		getRequest.header("authorization", authorization);
		ResultActions result;
		result = mvc.perform(getRequest);
		result.andDo(print());
		result.andExpect(status().isCreated());
		
		//try to add User again:
		result = mvc.perform(getRequest);
		result.andDo(print());
		result.andExpect(status().isConflict());
	}
	
	@Test
	public void testDelete() throws Exception {
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
		
		//add User with random name for deleting later
		String firstName = randomString(6);
		String lastName = randomString(7);
		String content = "{\"login\" : \"" + firstName + "_" + lastName + "\","
				+ "\"firstname\" : \"" + firstName + "\","
				+ "\"lastname\" : \"" + lastName + "\","
				+ "\"email\" : \"" + firstName + "." + lastName + "@dhbw-mannheim.de\","
				+ "\"deleted\" : 0,"
				+ "\"roles\" : [ ],"
				+ "\"password\" : \"" + firstName + "_" + lastName + "\"}";	
		MockHttpServletRequestBuilder postRequest1 = post("/sand/users/")
				.contentType(MediaType.APPLICATION_JSON)
				.content(content);
		postRequest1.header("authorization", authorization);
		ResultActions result;
		result = mvc.perform(postRequest1);
		result.andExpect(status().isCreated());
		User addedUser = mapper.readValue(result.andReturn().getResponse().getContentAsString(), User.class);
		
		//delete previously added user
		MockHttpServletRequestBuilder deleteRequest = delete("/sand/users/" + addedUser.getId());
		deleteRequest.header("authorization", authorization);
		result = mvc.perform(deleteRequest);
		result.andDo(print());
		result.andExpect(status().isOk());
		result.andExpect(content().string("true"));
		
		//now try to delete non existing user
		deleteRequest = delete("/sand/users/111");
		deleteRequest.header("authorization", authorization);
		result = mvc.perform(deleteRequest);
		result.andDo(print());
		result.andExpect(status().isNotFound());
	}
	
	@Test
	public void testChangePassword() throws Exception {
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
		
		//add User with random name for changing its password later
		String firstName = randomString(6);
		String lastName = randomString(7);
		String content = "{\"login\" : \"" + firstName + "_" + lastName + "\","
				+ "\"firstname\" : \"" + firstName + "\","
				+ "\"lastname\" : \"" + lastName + "\","
				+ "\"email\" : \"" + firstName + "." + lastName + "@dhbw-mannheim.de\","
				+ "\"deleted\" : 0,"
				+ "\"roles\" : [ ],"
				+ "\"password\" : \"" + firstName + "_" + lastName + "\"}";	
		postRequest = post("/sand/users/")
				.contentType(MediaType.APPLICATION_JSON)
				.content(content);
		postRequest.header("authorization", authorization);
		ResultActions result;
		result = mvc.perform(postRequest);
		result.andExpect(status().isCreated());
		User addedUser = mapper.readValue(result.andReturn().getResponse().getContentAsString(), User.class);
		
		//create object of PasswordChangeRequest with returned User and changed password and post it
		PasswordChangeRequest pwdRequest = new PasswordChangeRequest(addedUser, firstName + "_" + lastName, lastName + "_" + firstName);
		postRequest = post("/sand/users/password")
				.contentType(MediaType.APPLICATION_JSON)
				.content(writer.writeValueAsString(pwdRequest));
		result = mvc.perform(postRequest);
		result.andExpect(status().isOk());
		
		//try changing password of User which doesn't exist in DB with this ID
		addedUser.setId(111);
		pwdRequest = new PasswordChangeRequest(addedUser, firstName + "_" + lastName, lastName + "_" + firstName);
		postRequest = post("sand/users/password")
				.contentType(MediaType.APPLICATION_JSON)
				.content(writer.writeValueAsString(pwdRequest));
		result = mvc.perform(postRequest);
		result.andExpect(status().isNotFound());
		
		
	}
	
	private	String randomString(int len){
		final String AB = "abcdefghijklmnopqrstuvwxyz";
		Random rnd = new Random();
		StringBuilder sb = new StringBuilder( len );
		for(int i = 0; i < len; i++) {
			sb.append( AB.charAt(rnd.nextInt(AB.length())));
		}
		return sb.toString();
	}
	
}
