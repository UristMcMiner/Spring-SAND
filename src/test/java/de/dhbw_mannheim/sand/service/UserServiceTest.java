package de.dhbw_mannheim.sand.service;

import static org.junit.Assert.assertTrue;

import java.util.List;

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
import de.dhbw_mannheim.sand.model.User;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes=SAND.class)
@Transactional()
@Rollback(value=true)
public class UserServiceTest {

	@Autowired
	private UserService userService;
	
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
	public void testGetAllUsers() {
		List<User> users = userService.getAllUsers();
		assertTrue(users.size()==71);
	}

	@Test
	public void testHasUserWithLogin() {
		userService.deleteUserById(1);
		assertTrue(userService.hasUserWithLogin("Rainer_Colgen"));
	}

	@Test
	public void testGetUserById() {
		User user = userService.getUserById(13);
		System.out.println(user.getResearchProjects().size());
		assertTrue(user.getRoles().size()==1);
		assertTrue(user.getResearchProjects().size()==3);
	}

	@Test
	public void testGetUserByEmail() {
//		fail("Not yet implemented");
	}

	@Test
	public void testGetUserByLogin() {
		User user = userService.getUserByLogin("Felix_Bayer");
		assertTrue(user.getId()==5);
		assertTrue(user.getRoles().size()==1);
		System.out.println(user.getResearchProjects().size());
		assertTrue(user.getResearchProjects().size()==1);
	}

	@Test
	public void testGetUserByLoginAndPassword() {
		User user = userService.getUserByLoginAndPassword("Felix_Bayer", "Felix_Bayer");
		assertTrue(user.getId()==5);
	}

	@Test
	public void testAddUser() {
		User user = new User(null,"TestLogin", "First", "Last", "", "testlogin@test.de", "TestPassword");
		int id = userService.addUser(user);
		user.setId(id);
		user = userService.getUserByLoginAndPassword("TestLogin", "TestPassword");
		assertTrue(user.getId()>71);
	}

	@Test
	public void testEditUser() {
		User user = userService.getUserByLoginAndPassword("Rainer_Colgen", "Rainer_Colgen");
		user.setEmail("a@b.c");
		user.setPassword("Rainer_Colgen");
		userService.editUser(user);
		user = userService.getUserByLoginAndPassword("Rainer_Colgen", "Rainer_Colgen");
		assertTrue(user.getEmail().equals("a@b.c"));
	}

	@Test
	public void testDeleteUserById() {
		userService.deleteUserById(1);
		assertTrue(userService.getAllUsers().size()==70);
	}

	@Test
	public void testCheckPassword() {
//		fail("Not yet implemented");
	}

	@Test
	public void testChangePassword() {
		User user = userService.getUserById(1);
		user.setPassword("New Password");
		userService.changePassword(user);
		user = userService.getUserById(1);
		assertTrue(userService.checkPassword("New Password", user));
	}
	@Test
	public void testGetRolesByUser(){
		
	}
	@Test
	public void testGetProjectsByUser(){
		
	}

}
