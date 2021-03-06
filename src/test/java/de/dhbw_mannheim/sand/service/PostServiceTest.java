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
	}
	
	@Test
	public void testDeletePostById(){
		Post postBefore = postService.getPostById(1);
		assertTrue(postBefore.getHidden()==false);
		postService.deletePostById(1);
		Post postAfter = postService.getPostById(1);
		assertTrue(postAfter.getHidden()==true);

	}

	@Test
	public void testEditPostSuccess(){
	
		// Test No. 1
		int id = 1;
		Timestamp timestamp = new Timestamp((new Date()).getTime());
		User user = new User(1);
		Thread thread = new Thread(1);
		Post newPost = new Post(id, timestamp, "servus", user, thread, null, false);
		Post oldPost = postService.getPostById(id);
		
		postService.editPost(newPost);
		
		String newString = postService.getPostById(id).getText();
		assertEquals(oldPost.getId(), postService.getPostById(id).getId());
		assertNotEquals(oldPost.getText(), newString);
		assertEquals(oldPost.getThread(), postService.getPostById(id).getThread());
		assertEquals(oldPost.getCreator(), postService.getPostById(id).getCreator());
		assertNotEquals(oldPost.getTimestamp(), postService.getPostById(id).getTimestamp());
	
		// Test No. 2
/*		id = 34;
		user = new User(2);
		thread = new Thread(2);
		newPost = new Post(id, timestamp, "hallo", user, thread, null, false);
		oldPost = postService.getPostById(id);
		postService.editPost(newPost);*/
		
		//assertNotEquals(oldPost, postService.getPostById(newPost.getId()));
/*		assertEquals(oldPost.getId(), postService.getPostById(newPost.getId()).getId());
		assertNotEquals(oldPost.getText(), postService.getPostById(newPost.getId()).getText());
		assertEquals(oldPost.getThread(), postService.getPostById(newPost.getId()).getThread());
		assertEquals(oldPost.getCreator(), postService.getPostById(newPost.getId()).getCreator());
		assertNotEquals(oldPost.getTimestamp(), postService.getPostById(newPost.getId()).getTimestamp());*/
	}
	
	@Test
	public void testEditPostFailure(){
		Post post1 = postService.getPostById(2);
		post1.setId(2389232);
		postService.editPost(post1);
	}
	
	@Test
	public void testGetAllPostsByThreadId(){
		List <Post> posts = new ArrayList<>();
		for(Post post : postService.getAllPostsByThreadId(1, true)){
			posts.add(post);
		}
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
		assertTrue(post.getId()==1);

	}
	@Test
	public void testGetResearchProjectByPostId(){
		Integer id = 0;

		// Test 1: ReseachProject mit der Id=1 hat Post mit Id = 10
		id = postService.getResearchProjectByPostId(1);
		assertTrue(10 == id);

		// Test 2: ResearchProject mit der Id=10 hat Post mit Id=19
		id = postService.getResearchProjectByPostId(10);
		System.out.println(id);
		assertTrue(19 == id);

		// Test 3: Post hat kein dazugehöriges RP
		id = postService.getResearchProjectByPostId(41);
		assertNull(id);
	}

}
