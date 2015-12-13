package de.dhbw_mannheim.sand.controller;

import static org.junit.Assert.assertEquals;

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
import de.dhbw_mannheim.sand.model.Post;
import de.dhbw_mannheim.sand.model.ResearchProjectOffer;
import de.dhbw_mannheim.sand.model.User;
import de.dhbw_mannheim.sand.model.Thread;
import de.dhbw_mannheim.sand.repository.PostRepository;
import de.dhbw_mannheim.sand.repository.ResearchProjectOfferRepository;
import de.dhbw_mannheim.sand.service.PostService;
import de.dhbw_mannheim.sand.service.ResearchProjectOfferService;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes=SAND.class)
@Transactional
@Rollback(value=true)
public class PostControllerTest {

	@Autowired
	private PostController controller;
	
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
	public void testAdd(){
		Timestamp timestamp = new Timestamp((new Date()).getTime());
		User user = new User(1);
		Thread thread = new Thread(1);
		Post post = new Post(1);
		post.setCreator(user);
		post.setText("Test Post");
		post.setThread(thread);
		post.setTimestamp(timestamp);
		
		int newPostId = controller.add("X", post).getBody().getId();
		assertEquals(controller.getById("X", newPostId).getBody().getCreator(),post.getCreator());
		assertEquals(controller.getById("X", newPostId).getBody().getText(), post.getText());
		assertTrue(controller.getById("X", newPostId).getBody().getThread().getId() == post.getThread().getId());
		assertEquals(controller.getById("X", newPostId).getBody().getTimestamp(), post.getTimestamp());
	}
}
