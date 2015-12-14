package de.dhbw_mannheim.sand.aspects;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
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
import de.dhbw_mannheim.sand.service.ResearchProjectOfferService;
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
	 private UserService userService;
	 
	 @Autowired
	 private ResearchProjectOfferService rpoService;
	 
	 @Autowired
	 private ResearchProjectOfferControllerAuthorizationChecker checker;
	 
	 private User other;
	 private User student;
	 private User teacher;
	 private ResearchProjectOffer test;
	@Before
	public void setUp() throws ParseException {
		student = userService.getUserById(32);
		//Make Role of Student valid
		DateFormat formatter = new SimpleDateFormat("yyyy-dd-MM");
		student.getRoles().get(0).setEndDate(formatter.parse("2080-12-12"));
		teacher = userService.getUserById(2);//Joachim Schmidt
		other = userService.getUserById(4);
	}

	@Test
	public void testCheckGetById() throws Exception {
		Assert.assertTrue(checker.checkGetById(student, student.getResearchProjects().get(0).getId()));
		Assert.assertTrue(checker.checkGetById(teacher, student.getResearchProjects().get(0).getId()));
		Assert.assertTrue(checker.checkGetById(other, student.getResearchProjects().get(0).getId()));
	}
	
	@Test
	public void testCheckAdd() throws Exception {
		List<ResearchProject> rpo = student.getResearchProjects();
		for (ResearchProject rp : rpo){
		Assert.assertTrue(checker.checkAdd(student, rp));
		Assert.assertTrue(checker.checkAdd(teacher, rp));
		Assert.assertFalse(checker.checkAdd(other, rp));
		}
	}
	
	@Test
	public void testCheckUpdate() throws Exception {
		List<ResearchProject> rpo = student.getResearchProjects();
		for (ResearchProject rp : rpo){
			ResearchProjectOffer rp_c = (ResearchProjectOffer)rp;
			//Test Case: User is Creator
			Assert.assertTrue(checker.checkUpdate(student, rp));
			//Test Case: User is Creator and changes something
			ResearchProjectOffer rp_clone = new ResearchProjectOffer(rp.getId(),"TEST", rp.getCreator(),  rp.getDescription(), rp.getDescriptionLong(), rp_c.getVisible(), rp_c.getUsers(),rp_c.getThreads());
			Assert.assertTrue(checker.checkUpdate(student, rp_clone));
			//Test Case: Unauthorized user tries to change something
			Assert.assertFalse(checker.checkUpdate(teacher, rp_clone));
			Assert.assertFalse(checker.checkUpdate(teacher, rp));
		}
		//Get a ResearchProjectOffer different from 12
		//Change Users
		ResearchProjectOffer test = rpoService.getProjectById(18);
		List<User> users = new LinkedList<User>();
		for(User u : test.getUsers()){
			users.add(u);
		}
		users.add(student);
		ResearchProjectOffer test2 = new ResearchProjectOffer(test.getId(),test.getTitle(),test.getCreator(),test.getDescription(), test.getDescriptionLong(),test.getVisible(),users,test.getThreads());
		Assert.assertTrue(checker.checkUpdate(student, test2));
		Assert.assertFalse(checker.checkUpdate(teacher, test2));
	}
	@Test
	public void testCheckDelete() throws Exception {
		
	}
	
}
