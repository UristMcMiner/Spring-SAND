package de.dhbw_mannheim.sand.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import de.dhbw_mannheim.sand.SAND;
import de.dhbw_mannheim.sand.model.Event;
import de.dhbw_mannheim.sand.model.Post;
import de.dhbw_mannheim.sand.model.PostAttachment;
import de.dhbw_mannheim.sand.repository.PostAttachmentRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes=SAND.class)
@Transactional
@Rollback(value=true)
public class PostAttachmentServiceTest {

	@Autowired
	private PostAttachmentService service;
	
	@Autowired
	private PostAttachmentRepository repository;
	
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
	public void testGetAttachmentById() {
		
		int id = 12;
		int pID = 64;
		int eID = 12;
		PostAttachment expected = new PostAttachment(id);
		Post post = new Post(pID);
		Event event = new Event(eID);
		expected.setEvent(event);
		expected.setPost(post);

		PostAttachment actual = service.getPostAttachmentById(id);

		assertEquals(expected, actual);

	}

	@Test
	public void testGetAllAttachmentsByPostId() {
		int pId = 5;
		int attachCount = 4;

		List<PostAttachment> listAttachment = service.getAllPostAttachmentsByPostId(pId, false);

		assertEquals(attachCount, listAttachment.size());
	}

	@Test
	public void testAddAttachment() {
		int attId = -1;
		String attName = "Muster Attachment";
		int eId = 1;
		int pId = 1;
		Event event = new Event(eId);
		Post post = new Post(pId);

		PostAttachment expected = new PostAttachment(attId, post, event, attName);

		int oldCount = (int)repository.count();
		service.addPostAttachment(expected);
		int newCount = (int)repository.count();

		assertEquals(oldCount + 1, newCount);
	}

	@Test
	@Ignore
	public void testEditAttachment() {
		
		fail("Methode ist noch nicht implementiert");

	}

	@Test
	public void testDeleteAttachmentById() {
		int attId = 1;
		service.deleteAttachmentById(attId);

		boolean isDeleted = service.getPostAttachmentById(attId)==null;
		assertEquals(true, isDeleted);

	}

	@Test
	public void testDeleteAttachmentsByPostId() {
		int postId = 1;
		int notDeleted = countAttachmentsByPostID(postId, false);
		int delted = countAttachmentsByPostID(postId, true);

		service.deleteAttachmentsByPostId(postId);

		int count = countAttachmentsByPostID(postId, true);
		assertEquals(notDeleted + delted, count);

	}

	@Test
	public void testSetContentById() {
		int id = 1;
		String expected = "Das ist ein neuer Test";
		byte[] content = expected.getBytes();

		try {
			service.setContentById(id, content);

			InputStream loaded = service.getContentById(id);
			StringWriter writer = new StringWriter();
			IOUtils.copy(loaded, writer);
			String actual = writer.toString();
			assertEquals(expected, actual);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@Test
	public void testGetContentById() {
		int id = 1;
		String expected = "Hallo ich bin ein Post-Attachment";


		InputStream loaded = service.getContentById(id);
		try {
			StringWriter writer = new StringWriter();
			IOUtils.copy(loaded, writer);
			String actual = writer.toString();

			assertEquals(expected, actual);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	private int countAttachmentsByPostID(int id, boolean attDeleted) {
		
		int count=0;
		List<PostAttachment> pAs = repository.findAll();
		for(int i=0; i<pAs.size();i++)
		{
			if(pAs.get(i).getPost().getId() == id)
			{
				if((pAs.get(i).getDeleted()==1)==attDeleted)
				{
					count++;
				}
			}
		}
		return count;
	}
}
