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
	public void testGetThreadsById() {
		
		List<Thread> actual = new ArrayList<>();
		List<Thread> expected = new ArrayList<>();
		
		
		User creator = user_rep.findByLogin("Rainer_Colgen");
		
		ResearchProject rp = RPOR.save(new ResearchProjectOffer(2, "test_fuer_Threads", creator, "t", "test", true, new ArrayList<User>(), new ArrayList<Thread>()));
		Thread thread = new Thread(2, "test", new ArrayList<Post>(), rp);
		thread = thread_rep.save(thread);
		expected.add(thread);
		
		actual.add(thread_service.getThreadById(thread.getId()));
		
		assertEquals(expected, actual);
		
		// Test 2: Id gibt es nicht
		int threadId = 9999999;
		
		expected = new ArrayList<Thread>();
		
		expected.add(thread_service.getThreadById(threadId));
		
		assertEquals(true, expected.isEmpty());

//		// Test 3: zwei Rollen
//		userID = 68;
//		actual = new ArrayList<>();
//		beginDatum = new Date(2013, 01, 01);
//		endDatum = new Date(2018, 02, 02);
//		actual.add(new Teacher(8, new User(userID), beginDatum, endDatum, "9161-124469", "231D"));
//		beginDatum = new Date(1992, 01, 01);
//		endDatum = new Date(2000, 10, 15);
//		actual.add(new Teacher(9, new User(userID), beginDatum, endDatum, "9243-193179", "251A"));
//		expected = teacher.getRolesByUserId(userID);
//		assertEquals(actual.size(), expected.size());
//		assertEquals(actual.get(0).getId(), expected.get(0).getId());
//		assertEquals(actual.get(1).getId(), expected.get(1).getId());
//		assertEquals(actual.get(0).getUser(), expected.get(0).getUser());
//		assertEquals(actual.get(1).getUser(), expected.get(1).getUser());
//
//	}
//
//	@Test
//	public void testGetRoleById() {
//		int id = 0;
//		Date beginDatum = null;
//		Date endDatum = null;
//		Teacher staticTeacher = null;
//		Teacher functionTeacher = null;
//
//		// Test 1
//		id = 4;
//		beginDatum = new Date(2012, 10, 01);
//		endDatum = new Date(2015, 9, 30);
//		staticTeacher = new Teacher(id, new User(51), beginDatum, endDatum, "9999-999999", "321X");
//		functionTeacher = (Teacher) teacher.getRoleById(id);
//		assertEquals(staticTeacher.getPhone(), functionTeacher.getPhone());
//		assertEquals(staticTeacher.getRoom(), functionTeacher.getRoom());
//		assertEquals(staticTeacher.getId(), functionTeacher.getId());
//		assertEquals(staticTeacher.getUser(), functionTeacher.getUser());
//		// assertEquals(staticTeacher.getEndDate(), functionTeacher.getEndDate());
//		// assertEquals(staticTeacher.getStartDate(), functionTeacher.getStartDate());
//
//		// Test 2
//		id = 8;
//		functionTeacher = (Teacher) teacher.getRoleById(id);
//		assertNotSame(staticTeacher.getPhone(), functionTeacher.getPhone());
//		assertNotSame(staticTeacher.getRoom(), functionTeacher.getRoom());
//		assertNotSame(staticTeacher.getId(), functionTeacher.getId());
//		assertNotSame(staticTeacher.getUser(), functionTeacher.getUser());
//		// assertEquals(staticTeacher.getEndDate(), functionTeacher.getEndDate());
//		// assertEquals(staticTeacher.getStartDate(), functionTeacher.getStartDate());
//
//		// Test 3: Negativ-Test
//		id = 999999;
//		staticTeacher = null;
//		functionTeacher = (Teacher) teacher.getRoleById(id);
//		assertEquals(staticTeacher, functionTeacher);
//	}
//
//
//	
//	@Test
//	public void testAddRoleSuccess() {
//
//		// Test 1
//		int id = 99999;
//		Date beginDatum = new Date(2012, 10, 01);
//		Date endDatum = new Date(2015, 9, 30);
//		Teacher staticTeacher = new Teacher(id, new User(2), beginDatum, endDatum, "38376", "123E");
//		long beforeCounter = 0;
//		long afterCounter = 0;
//
//		beforeCounter =  teacher_rep.count();
//		id = teacher.addRole(staticTeacher);
//		afterCounter = teacher_rep.count();
//		
//		// Objekt mit Id aufrufen
//		Teacher functionTeacher = (Teacher) teacher.getRoleById(id);
//		assertEquals(afterCounter, beforeCounter + 1);
//		assertEquals(staticTeacher.getPhone(), functionTeacher.getPhone());
//		assertEquals(staticTeacher.getRoom(), functionTeacher.getRoom());
//		assertEquals(staticTeacher.getUser(), functionTeacher.getUser());
//		assertEquals(staticTeacher.getEndDate(), functionTeacher.getEndDate());
//		assertEquals(staticTeacher.getStartDate(), functionTeacher.getStartDate());
//	}
//
//	@Test
//	public void testEditRoleSuccess() {
//		// Test 1
//		int id = 7;
//		Date beginDatum = new Date(2012, 10, 01);
//		Date endDatum = new Date(2015, 9, 30);
//		Teacher staticTeacher = new Teacher(id, new User(64), beginDatum, endDatum, "9743-971347", "232D");
//		teacher.editRole(staticTeacher);
//		Teacher functionTeacher = (Teacher) teacher.getRoleById(7);
//		assertEquals(staticTeacher.getRoom(), functionTeacher.getRoom());
//		assertEquals(staticTeacher.getPhone(), functionTeacher.getPhone());
//		assertEquals(staticTeacher.getUser(), functionTeacher.getUser());
//		assertEquals(staticTeacher.getEndDate(), functionTeacher.getEndDate());
//		assertEquals(staticTeacher.getStartDate(), functionTeacher.getStartDate());
//		assertEquals(staticTeacher.getId(), functionTeacher.getId());
//	}
//
//	@Test(expected = RuntimeException.class)
//	public void testEditRoleFail() {
//		Teacher functionTeacher = (Teacher) teacher.getRoleById(67849);
//		teacher.addRole(functionTeacher);
//	}
//
//	@Test
//	@Ignore
//	public void testDeleteRoleByIDSuccess() {
//		teacher.deleteRoleById(2);
//		Date endDatum = new Date(2015, 05, 25);
//		assertEquals(endDatum, teacher.getRoleById(2).getEndDate());
//	}
//
//	@Test(expected = RuntimeException.class)
//	public void testDeleteRoleByIdFail() {
//		teacher.deleteRoleById(437234534);
	}
}
