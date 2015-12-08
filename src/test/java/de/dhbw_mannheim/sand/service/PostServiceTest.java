package de.dhbw_mannheim.sand.service;

import static org.junit.Assert.*;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
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
import de.dhbw_mannheim.sand.model.Thread;
import de.dhbw_mannheim.sand.model.User;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes=SAND.class)
@Transactional()
@Rollback(value=true)
public class PostServiceTest {
	
	@Autowired
	private PostService postService;
	
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
	public void testAddPost(){
		Timestamp timestamp = new Timestamp((new Date()).getTime());
		User user = new User(1);
		Thread thread = new Thread(1);
		Post post = new Post(1);
		post.setCreator(user);
		post.setText("Testing add new post");
		post.setThread(thread);
		post.setTimestamp(timestamp);
		
		int newPostId = postService.addPost(post);
		assertEquals(postService.getPostById(newPostId).getCreator(),post.getCreator());
		assertEquals(postService.getPostById(newPostId).getText(), post.getText());
		assertTrue(postService.getPostById(newPostId).getThread().getId() == post.getThread().getId());
		assertEquals(postService.getPostById(newPostId).getTimestamp(), post.getTimestamp());

		System.out.println("done AddPost");
	}
	@Test
	public void testdeletePostById(){
		Post postBefore = postService.getPostById(1);
		assertNotNull(postBefore);
		postService.deletePostById(1);
		Post postAfter = postService.getPostById(1);

		System.out.println("done deletePostById");
		assertNull(postAfter);

	}

	@Test
	public void testEditPost(){
		
	}
	
	@Test
	public void testGetAllPostsByThreadId(){
		List <Post> posts = new ArrayList<>();
		for(Post post : postService.getAllPostsByThreadId(1, true)){
			posts.add(post);
		}

		System.out.println("done getAllPostsByThreadID");
		assertTrue(posts.size() == 3);
		
		assertTrue(posts.get(0).getId() == 1);
		assertTrue(posts.get(0).getCreator().getId() == 1);
		
		assertTrue(posts.get(1).getId() == 34);
		assertTrue(posts.get(1).getCreator().getId() == 10);
		
		assertTrue(posts.get(2).getId() == 67);
		assertTrue(posts.get(2).getCreator().getId() == 13);
			
	}
	
	@Test
	public void testGetPostById(){
		Post post = postService.getPostById(1);

		System.out.println("done getPostById");
		assertTrue(post.getId()==1);

	}
	@Test
	public void testGetResearchProjectByPostId(){
		int id = 0;

		// Test 1: ReseachProject mit der Id=10 hat Post mit Id = 3
		id = postService.getResearchProjectByPostId(4);
		System.out.println(id);
		assertTrue(13 == id);

		// Test 2: ResearchProject mit der Id=23 hat Post mit Id=
		id = postService.getResearchProjectByPostId(11);
		System.out.println(id);
		assertTrue(20 == id);

		// Test 3: Post hat kein dazugehöriges RP
		id = postService.getResearchProjectByPostId(6);
		System.out.println(id);

		System.out.println("done getresearchprojectbypostid");
		assertEquals(null, id);
	}

}
