package de.dhbw_mannheim.sand.aspects;

import java.util.Date;
import java.util.List;
import java.util.Random;

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
	 ResearchProjectOfferRepository repository;

	 @Autowired
	 UserService service;
	 

	 ResearchProjectOfferControllerAuthorizationChecker checker;
	 
	 User student;
	 User teacher;

	@Before
	public void setUp() {
		student = service.getUserById(51);
		teacher = service.getUserById(2);//Joachim Schmidt
		checker = new ResearchProjectOfferControllerAuthorizationChecker();
	}

	@Test
	public void testCheckGetById() throws Exception {
		for(Role role : student.getRoles()){
			System.out.println(role.toString());
		}
		for(Role role : teacher.getRoles()){
			System.out.println(role.toString());
		}

		
	}
	
	@Test
	public void testCheckAdd() throws Exception {
		
	}
	
	@Test
	public void testCheckUpdate() throws Exception {
		List<ResearchProject> rpo = student.getResearchProjects();
		System.out.println(checker.checkUpdate(student, (ResearchProjectOffer)rpo.get(0) ));
	}
	@Test
	public void testCheckDelete() throws Exception {
		
	}
	
}
