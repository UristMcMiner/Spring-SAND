package de.dhbw_mannheim.sand.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Date;

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
import de.dhbw_mannheim.sand.model.Post;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = SAND.class)
@Transactional
@Rollback(value=true)
@WebAppConfiguration
public class PostControllerTest {

	@Autowired
	private WebApplicationContext context;

	private MockMvc mvc;

	@Before
	public void setUp() {
		this.mvc = MockMvcBuilders.webAppContextSetup(this.context).build();
	}
	
	@Test
	public void testGetById() throws Exception {
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
		MockHttpServletRequestBuilder getRequest = get("/sand/posts/1");
		getRequest.header("authorization", authorization);
		ResultActions result;
		result = mvc.perform(getRequest);
		result.andDo(print());
		result.andExpect(status().isOk());
		getRequest = get("/sand/posts/9999");
		getRequest.header("authorization", authorization);
		result = mvc.perform(getRequest);
		result.andDo(print());
		result.andExpect(status().isNotFound());
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
		System.out.println("AUTH: "+authorization);
				
		//generate Post JSON
		String content = "{\"timestamp\" : " + (new Date()).getTime() + ","
				+ "\"text\" : \"Dies ist ein Post.\","
				+ "\"creator\" : {\"id\" : 1},"
				+ "\"thread\" : {\"id\" : 1},"
				+ "\"attachments\" : [],"
				+ "\"hidden\" : false}";
		
		postRequest = post("/sand/posts/")
				.contentType(MediaType.APPLICATION_JSON)
				.content(content);
		postRequest.header("authorization", authorization);
		ResultActions result = mvc.perform(postRequest);
		result.andDo(print());
		result.andExpect(status().isCreated());
		result.andExpect(jsonPath("$.text").value("Dies ist ein Post."));
		result.andExpect(jsonPath("$.thread.id").value(1));
		result.andExpect(jsonPath("$.creator.id").value(1));
	}
	
	@Test
	public void testEdit() throws Exception {
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
		
		//generate Post JSON
		String content = "{\"timestamp\" : " + (new Date()).getTime() + ","
				+ "\"text\" : \"Dies ist ein Post.\","
				+ "\"creator\" : {\"id\" : 1},"
				+ "\"thread\" : {\"id\" : 1},"
				+ "\"attachments\" : [],"
				+ "\"hidden\" : false}";
		
		postRequest = post("/sand/posts/")
				.contentType(MediaType.APPLICATION_JSON)
				.content(content);
		postRequest.header("authorization", authorization);
		ResultActions result = mvc.perform(postRequest);
		int postID = mapper.readValue(result.andReturn().getResponse().getContentAsString(), Post.class).getId();

		//edit Post JSON
		content = "{\"id\" : " + postID + ","
				+ "\"timestamp\" : " + (new Date()).getTime() + ","
				+ "\"text\" : \"Dies ist ein geänderter Post.\","
				+ "\"creator\" : {\"id\" : 1},"
				+ "\"thread\" : {\"id\" : 1},"
				+ "\"attachments\" : [],"
				+ "\"hidden\" : false}";
		
		MockHttpServletRequestBuilder putRequest = put("/sand/posts/")
				.contentType(MediaType.APPLICATION_JSON)
				.content(content);
		putRequest.header("authorization", authorization);
		result = mvc.perform(putRequest);
		result.andDo(print());
		result.andExpect(status().isForbidden());
		
		//edit Post JSON (set hidden to true)
		content = "{\"id\" : " + postID + ","
				+ "\"timestamp\" : " + (new Date()).getTime() + ","
				+ "\"text\" : \"Dies ist ein Post.\","
				+ "\"creator\" : {\"id\" : 1},"
				+ "\"thread\" : {\"id\" : 1},"
				+ "\"attachments\" : [],"
				+ "\"hidden\" : true}";
		
		putRequest = put("/sand/posts/")
				.contentType(MediaType.APPLICATION_JSON)
				.content(content);
		putRequest.header("authorization", authorization);
		result = mvc.perform(putRequest);
		result.andDo(print());
		result.andExpect(status().isOk());
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
		System.out.println("AUTH: "+authorization);
		
		MockHttpServletRequestBuilder deleteRequest = delete("/sand/posts/1");
		deleteRequest.header("authorization", authorization);
		ResultActions result = mvc.perform(deleteRequest);
		result.andDo(print());
		result.andExpect(status().isForbidden());
	}	
}