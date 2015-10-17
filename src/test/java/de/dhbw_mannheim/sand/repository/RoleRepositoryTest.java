package de.dhbw_mannheim.sand.repository;

import static org.junit.Assert.*;

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
import de.dhbw_mannheim.sand.model.Role;
import de.dhbw_mannheim.sand.model.User;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes=SAND.class)
@Transactional()
@Rollback(value=true)
public class RoleRepositoryTest {

	@Autowired
	private StudentRepository roleRepository;
	
	@Autowired
	private UserRepository userRepository;
		
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
	public void testFindByUser() {
		User user = userRepository.findOne(1);
		List<Role> roles = roleRepository.findByUser(user);
		System.out.println(roles.size());
	}

}
