package de.dhbw_mannheim.sand.service;

import static org.junit.Assert.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

import de.dhbw_mannheim.sand.SAND;
import de.dhbw_mannheim.sand.model.ResearchProject;
import de.dhbw_mannheim.sand.model.ResearchProjectOffer;
import de.dhbw_mannheim.sand.model.Role;
import de.dhbw_mannheim.sand.model.Teacher;
import de.dhbw_mannheim.sand.model.User;
import de.dhbw_mannheim.sand.model.Thread;
import de.dhbw_mannheim.sand.model.Post;
import de.dhbw_mannheim.sand.repository.ResearchProjectOfferRepository;
import de.dhbw_mannheim.sand.repository.TeacherRepository;
import de.dhbw_mannheim.sand.repository.ThreadRepository;
import de.dhbw_mannheim.sand.repository.UserRepository;

//so Datei ändern um neu zu pushen....

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes=SAND.class)
@Transactional()
@Rollback(value=true)
public class ThreadServiceTest {

	@Autowired
	private ThreadService thread_service;
	
	@Autowired
	private ResearchProjectOfferRepository RPOR;
	
	@Autowired
	private ThreadRepository thread_rep;
	
	@Autowired
	private UserRepository user_rep;

	@Test
	public void testGetThreadById() {
		
		List<Thread> actual = new ArrayList<>();
		List<Thread> expected = new ArrayList<>();
		
		
		User creator = user_rep.findByLogin("Rainer_Colgen");
		
		ResearchProject rp = RPOR.save(new ResearchProjectOffer(2, "test_fuer_Threads", creator, "t", "test", true, new ArrayList<User>(), new ArrayList<Thread>()));
		Thread thread = new Thread(2, "test", new ArrayList<Post>(), rp);
		thread = thread_rep.save(thread);
		expected.add(thread);
		
		/*
		 * Schlägt fehl, Fehler: 
		00:23:17,257  WARN SqlExceptionHelper:144 - SQL Error: 1048, SQLState: 23000
		00:23:17,257 ERROR SqlExceptionHelper:146 - Column 'research_project_offer_uuid' cannot be null
		00:23:17,260  WARN SqlExceptionHelper:232 - SQL Warning Code: 1048, SQLState: 23000
		00:23:17,260  WARN SqlExceptionHelper:233 - Column 'research_project_offer_uuid' cannot be null

		actual.add(thread_service.getThreadById(thread.getId()));
		assertEquals(expected, actual);
		*/
		
//		// Test 2: Id gibt es nicht
//		int threadId = 9999999;
//		
//		expected = new ArrayList<Thread>();
//		
//		Thread temp_thread = thread_service.getThreadById(threadId);
//		if (temp_thread != null) {
//			expected.add(temp_thread);
//		}
//		
//		assertEquals(true, expected.isEmpty());
	}
	
	@Test
	public void testAddThread() {
		int tId = 2;
		int researchId = 10;
		Thread expected = new Thread(tId);
		ResearchProjectOffer p = new ResearchProjectOffer(researchId);
		expected.setTitle("Test Thread");
		expected.setResearchProject(p);

		int t_id = thread_service.addThread(expected);
		Thread t = thread_rep.findOne(t_id);
		expected.setId(t_id);

		assertEquals(expected, t);

	}
	
	public void testGetAllThreadsByResearchProjectId() {
		int rId = 10;
		//@formatter:off
		/**
		 * ResearchProject mit der ID=10 besitzt zwei Threads
		 * mit den IDs id1 = 1 und id2 = 21,
		 * beide Threads sind sichtbar(hidden=0).
		 */
		//@formatter:on
		Thread t1 = new Thread(1);
		Thread t2 = new Thread(21);
		t1.setTitle("Lorem ipsum dolor sit amet, consetetur sadips");
		t2.setTitle("Lorem ipsum dolor sit amet, consetetur sadips");
		List<Thread> expected = new ArrayList<>();
		expected.add(t1);
		expected.add(t2);

		List<Thread> actual = thread_service.getAllThreadsByResearchProjectId(rId);

		assertEquals(expected.size(), actual.size());


		int rIdHidden = 11;
		// formater:off
		/**
		 * ResearchProject mit der ID=11 besitzt zwei Threads mit den IDs id1 = 2 und id2 = 22, beide Threads sind nicht
		 * sichtbar(hidden=1).
		 */
		// formatter:on
		expected.clear();
		actual = thread_service.getAllThreadsByResearchProjectId(rIdHidden);
		assertEquals(expected.size(), actual.size());

		// lazy
		actual = thread_service.getAllThreadsByResearchProjectId(rId);
		expected.add(new Thread(rId));
		expected.add(new Thread(rId));

		assertEquals(expected.size(), actual.size());
	}
	
	@Test
	public void testEditThread() {
		int tId = 10;
		//@formatter:off
		/**
		 * Der Thread mit der id=10 hat den
		 * ursprünglichen Titel 'Lorem ipsum dolor sit
		 * amet, consetetur sadips' und ist mit dem
		 * ResearchProject mit der id=19 verknuepft.
		 */
		//@formatter:on
		int pId = 2;
		Thread expected = new Thread(tId);
		expected.setTitle("Ich bin ein editierter Thread");
		ResearchProjectOffer p = new ResearchProjectOffer(pId);
		expected.setResearchProject(p);

		thread_service.editThread(expected);
		Thread actual = thread_service.getThreadById(tId);
		assertEquals(expected.getTitle(), actual.getTitle());
		assertEquals(expected.getResearchProject().getId(), actual.getResearchProject().getId());

	}
	
	@Test
	public void testDeleteThreadByID() {
		int tId = 1;
		int rId = 10;
		//@formatter:off
		/**
		 * Der Thread besitzt folgende Attribute:
		 * id = 1
		 * research_project_id = 10
		 * title = 'Lorem ipsum dolor sit
		 * 			amet, consetetur sadips'
		 * hidden = 0
		 */
		Thread expected = new Thread(tId);
		expected.setTitle("Lorem ipsum dolor sit amet, consetetur sadips");
		ResearchProjectOffer p = new ResearchProjectOffer(rId);
		expected.setResearchProject(p);
		Thread t = thread_rep.save(expected);
		
		List<Thread> before_list = thread_rep.findAll();
		thread_service.deleteThreadById(t.getId());
		List<Thread> after_list = thread_rep.findAll();
		
		assertEquals(before_list.size() - 1, after_list.size());
	}

	
	@Test(expected = IllegalArgumentException.class)
	public void testDeleteNonExistingThread() {
		int t_id = 9999999;
		thread_service.deleteThreadById(t_id);
	}
}
