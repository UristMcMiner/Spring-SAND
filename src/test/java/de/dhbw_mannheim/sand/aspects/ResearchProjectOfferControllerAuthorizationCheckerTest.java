package de.dhbw_mannheim.sand.aspects;

import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.transaction.Transactional;

import org.junit.Assert;
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
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import de.dhbw_mannheim.sand.SAND;
import de.dhbw_mannheim.sand.aspects.authorization.ResearchProjectOfferControllerAuthorizationChecker;
import de.dhbw_mannheim.sand.model.Admin;
import de.dhbw_mannheim.sand.model.Login;
import de.dhbw_mannheim.sand.model.PasswordChangeRequest;
import de.dhbw_mannheim.sand.model.ResearchProject;
import de.dhbw_mannheim.sand.model.ResearchProjectOffer;
import de.dhbw_mannheim.sand.model.Role;
import de.dhbw_mannheim.sand.model.User;
import de.dhbw_mannheim.sand.repository.ResearchProjectOfferRepository;
import de.dhbw_mannheim.sand.service.UserService;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = SAND.class)
@Transactional
@Rollback(value=true)
@WebAppConfiguration
public class ResearchProjectOfferControllerAuthorizationCheckerTest {

	 @Autowired
	 private WebApplicationContext context;
	 
	 @Autowired
	 private ResearchProjectOfferRepository repository;

	 @Autowired
	 private UserService service;

	 @Autowired
	 private ResearchProjectOfferControllerAuthorizationChecker checker;
	 
	 private User student;
	 private User teacher;
	 private ResearchProjectOffer test;
	@Before
	public void setUp() {
		//Kein Student mit ResearchProject hat eine gültige Studentenrolle, muss in der Datenbank angepasst werden
		student = service.getUserById(32);
		teacher = service.getUserById(2);//Joachim Schmidt
	}

	@Test
	public void testCheckGetById() throws Exception {
		Assert.assertTrue(checker.checkGetById(student, student.getResearchProjects().get(0).getId()));
		Assert.assertTrue(checker.checkGetById(teacher, student.getResearchProjects().get(0).getId()));
	}
	
	@Test
	public void testCheckAdd() throws Exception {
		List<ResearchProject> rpo = student.getResearchProjects();
		for (ResearchProject rp : rpo){
		Assert.assertTrue(checker.checkAdd(student, rp));
		Assert.assertTrue(checker.checkAdd(teacher, rp));
		}
	}
	
	@Test
	public void testCheckUpdate() throws Exception {
		List<ResearchProject> rpo = student.getResearchProjects();
		for (ResearchProject rp : rpo){
			if(rp instanceof ResearchProjectOffer){
			Assert.assertTrue(checker.checkUpdate(student, rp));
			Assert.assertFalse(checker.checkUpdate(teacher, rp));
			}
		}
	}
	@Test
	public void testCheckDelete() throws Exception {
		
	}
	
}
