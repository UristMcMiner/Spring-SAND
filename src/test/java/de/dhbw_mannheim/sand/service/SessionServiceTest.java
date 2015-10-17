package de.dhbw_mannheim.sand.service;

import static org.junit.Assert.*;

import java.util.UUID;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import de.dhbw_mannheim.sand.SAND;
import de.dhbw_mannheim.sand.model.Session;
import de.dhbw_mannheim.sand.model.User;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes=SAND.class)
@Transactional
@Rollback(value=true)
public class SessionServiceTest {

	@Autowired
	private UserService userService;
	
	@Autowired
	private SessionService sessionService;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testAddSession() {
		User user = userService.getUserById(1);
		Session session = new Session(null,user);
		UUID uuid = sessionService.addSession(session);
		session = sessionService.getSessionById(uuid);
		assertTrue(session.getUser().getLogin().equals("Rainer_Colgen"));
		sessionService.deleteSessionById(uuid);
		session = sessionService.getSessionById(uuid);
		assertTrue(session==null);
	}

	@Test
	public void testGetSessionById() {
	}

	@Test
	public void testDeleteSessionById() {
	}

	@Test
	public void testEditSession() {
	}

}
