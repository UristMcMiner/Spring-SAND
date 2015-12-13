package de.dhbw_mannheim.sand.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import java.util.UUID;

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
import de.dhbw_mannheim.sand.model.ResearchProjectOffer;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = SAND.class)
@Transactional
@Rollback(value=true)
@WebAppConfiguration
public class ResearchProjectOfferControllerTest {

	@Autowired
	private WebApplicationContext context;

	private MockMvc mvc;

	@Before
	public void setUp() {
		this.mvc = MockMvcBuilders.webAppContextSetup(this.context).build();
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
		
		MockHttpServletRequestBuilder getRequest = get("/sand/researchprojectoffer/");
		getRequest.header("authorization", authorization);
		ResultActions result;
		result = mvc.perform(getRequest);
		result.andDo(print());
		result.andExpect(status().isOk());
		List<ResearchProjectOffer> rpoList = mapper.readValue(result.andReturn().getResponse().getContentAsString(), mapper.getTypeFactory().constructCollectionType(List.class, ResearchProjectOffer.class));
		System.out.println("Size of Researchprojectofferlist: " + rpoList.size());
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
		
		MockHttpServletRequestBuilder getRequest = get("/sand/researchprojectoffer/2");
		getRequest.header("authorization", authorization);
		ResultActions result;
		result = mvc.perform(getRequest);
		result.andDo(print());
		result.andExpect(status().isOk());
		getRequest = get("/sand/researchprojectoffer/9999");
		getRequest.header("authorization", authorization);
		result = mvc.perform(getRequest);
		result.andDo(print());
		result.andExpect(status().isNotFound());
	}
	
	@Test
	public void testGetByUuid() throws Exception {
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
		
		MockHttpServletRequestBuilder getRequest = get("/sand/researchprojectoffer/private/fa4b5497-029a-11e5-8f22-000c29433838");
		getRequest.header("authorization", authorization);
		ResultActions result;
		result = mvc.perform(getRequest);
		result.andDo(print());
		result.andExpect(status().isOk());
		getRequest = get("/sand/researchprojectoffer/private/ffffffff-ffff-ffff-ffff-ffffffffffff");
		getRequest.header("authorization", authorization);
		result = mvc.perform(getRequest);
		result.andDo(print());
		result.andExpect(status().isInternalServerError());

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
				
		//generate RPO JSON
		String content = "{\"type\" : \"offer\","
				+ "\"title\" : \"ResearchProjectOfferTitle\","
				+ "\"creator\" : {\"id\" : 1},"
				+ "\"description\" : \"Beschreibung\","
				+ "\"descriptionLong\" : \"lange Beschreibung\","
				+ "\"threads\" : [],"
				+ "\"deleted\" : 0,"
				+ "\"visible\" : true,"
				+ "\"users\" : [{\"id\":1}],"
				+ "\"uuid\" : \"" + UUID.randomUUID() + "\"}";
		
		postRequest = post("/sand/researchprojectoffer/")
				.contentType(MediaType.APPLICATION_JSON)
				.content(content);
		postRequest.header("authorization", authorization);
		ResultActions result = mvc.perform(postRequest);
		result.andDo(print());
		result.andExpect(status().isOk());
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
		
		//generate RPO JSON
		String content = "{\"type\" : \"offer\","
				+ "\"title\" : \"ResearchProjectOfferTitle\","
				+ "\"creator\" : {\"id\" : 1},"
				+ "\"description\" : \"Beschreibung\","
				+ "\"descriptionLong\" : \"lange Beschreibung\","
				+ "\"threads\" : [],"
				+ "\"deleted\" : 0,"
				+ "\"visible\" : true,"
				+ "\"users\" : [{\"id\":1}],"
				+ "\"uuid\" : \"" + UUID.randomUUID() + "\"}";
		
		postRequest = post("/sand/researchprojectoffer/")
				.contentType(MediaType.APPLICATION_JSON)
				.content(content);
		postRequest.header("authorization", authorization);
		ResultActions result = mvc.perform(postRequest);
		result.andDo(print());
		result.andExpect(status().isOk());
		int rpoID = Integer.valueOf(result.andReturn().getResponse().getContentAsString().split("\"id\"")[1].split(",")[0].substring(1));
		
		//edit RPO JSON
		content = "{\"type\" : \"offer\","
				+ "\"id\" : " + rpoID + ","
				+ "\"title\" : \"ResearchProjectOfferTitle\","
				+ "\"creator\" : {\"id\" : 1},"
				+ "\"description\" : \"Beschreibung\","
				+ "\"descriptionLong\" : \"geänderte Beschreibung\","
				+ "\"threads\" : [],"
				+ "\"deleted\" : 0,"
				+ "\"visible\" : true,"
				+ "\"users\" : [{\"id\":1}],"
				+ "\"uuid\" : \"" + UUID.randomUUID() + "\"}";
		
		MockHttpServletRequestBuilder putRequest = put("/sand/researchprojectoffer/")
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
		
		//generate RPO JSON for deleting later
		String content = "{\"type\" : \"offer\","
				+ "\"title\" : \"ResearchProjectOfferTitle\","
				+ "\"creator\" : {\"id\" : 1},"
				+ "\"description\" : \"Beschreibung\","
				+ "\"descriptionLong\" : \"lange Beschreibung\","
				+ "\"threads\" : [],"
				+ "\"deleted\" : 0,"
				+ "\"visible\" : true,"
				+ "\"users\" : [{\"id\":1}],"
				+ "\"uuid\" : \"" + UUID.randomUUID() + "\"}";
		
		postRequest = post("/sand/researchprojectoffer/")
				.contentType(MediaType.APPLICATION_JSON)
				.content(content);
		postRequest.header("authorization", authorization);
		ResultActions result = mvc.perform(postRequest);
		int rpoID = Integer.valueOf(result.andReturn().getResponse().getContentAsString().split("\"id\"")[1].split(",")[0].substring(1));
		
		MockHttpServletRequestBuilder deleteRequest = delete("/sand/researchprojectoffer/" + rpoID);
		deleteRequest.header("authorization", authorization);
		result = mvc.perform(deleteRequest);
		result.andDo(print());
		result.andExpect(status().isOk());
		
		//try deleting non existing rpo
		deleteRequest = delete("/sand/researchprojectoffer/9999");
		deleteRequest.header("authorization", authorization);
		result = mvc.perform(deleteRequest);
		result.andDo(print());
		result.andExpect(status().isInternalServerError());
	}	
}