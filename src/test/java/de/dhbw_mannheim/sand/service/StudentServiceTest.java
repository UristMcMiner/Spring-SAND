package de.dhbw_mannheim.sand.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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

import de.dhbw_mannheim.sand.SAND;
import de.dhbw_mannheim.sand.model.Course;
import de.dhbw_mannheim.sand.model.Role;
import de.dhbw_mannheim.sand.model.Student;
import de.dhbw_mannheim.sand.model.User;
import de.dhbw_mannheim.sand.repository.StudentRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes=SAND.class)
@Transactional()
@Rollback(value=true)
public class StudentServiceTest {

	@Autowired
	private StudentService student;
	
	@Autowired
	private StudentRepository student_rep;

	@Test
	public void testGetRolesByUserId() {

		int userID = 0;
		List<Role> actual = new ArrayList<>();
		List<Role> expected = new ArrayList<>();

		// Test 1: nur eine Rolle
		userID = 25;
		Date beginDatum = new Date(2012, 10, 01);
		Date endDatum = new Date(2015, 9, 30);
		actual.add(new Student(21, new User(userID), beginDatum, endDatum, "" + 1743484, new Course(1)));
		expected = student.getRolesByUserId(userID);
		assertEquals(((Student)actual.get(0)).getCourse(), ((Student)expected.get(0)).getCourse());
		assertEquals(actual.get(0).getId(), expected.get(0).getId());
		assertEquals(((Student)actual.get(0)).getEnrollmentNumber(), ((Student)expected.get(0)).getEnrollmentNumber());
		assertEquals(actual.get(0).getUser(), expected.get(0).getUser());

		// Test 2: Id gibt es nicht
		userID = 9999999;
		expected = student.getRolesByUserId(userID);
		assertEquals(true, expected.isEmpty());

		// Test 3: zwei Rollen
		userID = 57;
		actual = new ArrayList<>();
		beginDatum = new Date(2013, 01, 01);
		endDatum = new Date(2018, 02, 02);
		actual.add(new Student(54, new User(userID), beginDatum, endDatum, "" + 9999996, new Course(9)));
		beginDatum = new Date(1992, 01, 01);
		endDatum = new Date(2000, 10, 15);
		actual.add(new Student(60, new User(userID), beginDatum, endDatum, "" + 9999990, new Course(7)));
		expected = student.getRolesByUserId(userID);
		assertEquals(actual.size(), expected.size());
		assertEquals(actual.get(0).getId(), expected.get(0).getId());
		assertEquals(actual.get(1).getId(), expected.get(1).getId());
		assertEquals(actual.get(0).getUser(), expected.get(0).getUser());
		assertEquals(actual.get(1).getUser(), expected.get(1).getUser());

	}

	@Test
	public void testGetRoleById() {
		int id = 0;
		Date beginDatum = null;
		Date endDatum = null;
		Student staticStudent = null;
		Student functionStudent = null;

		// Test 1
		id = 21;
		beginDatum = new Date(2012, 10, 01);
		endDatum = new Date(2015, 9, 30);
		staticStudent = new Student(id, new User(25), beginDatum, endDatum, "" + 1743484, new Course(1));
		functionStudent = (Student) student.getRoleById(id);
		assertEquals(staticStudent.getCourse(), functionStudent.getCourse());
		assertEquals(staticStudent.getEnrollmentNumber(), functionStudent.getEnrollmentNumber());
		assertEquals(staticStudent.getId(), functionStudent.getId());
		assertEquals(staticStudent.getUser(), functionStudent.getUser());
		// assertEquals(staticStudent.getEndDate(), functionStudent.getEndDate());
		// assertEquals(staticStudent.getStartDate(), functionStudent.getStartDate());

		// Test 2
		id = 45;
		functionStudent = (Student) student.getRoleById(id);
		assertNotSame(staticStudent.getCourse(), functionStudent.getCourse());
		assertNotSame(staticStudent.getEnrollmentNumber(), functionStudent.getEnrollmentNumber());
		assertNotSame(staticStudent.getId(), functionStudent.getId());
		assertNotSame(staticStudent.getUser(), functionStudent.getUser());
		// assertEquals(staticStudent.getEndDate(), functionStudent.getEndDate());
		// assertEquals(staticStudent.getStartDate(), functionStudent.getStartDate());

		// Test 3 - Negativ-Test
		id = 999999;
		staticStudent = null;
		functionStudent = (Student) student.getRoleById(id);
		assertEquals(staticStudent, functionStudent);
	}

	@Test
	public void testAddRoleSuccess() {

		//Test 1
		int id = 99999;
		Date beginDatum = new Date(2012, 10, 01);
		Date endDatum = new Date(2015, 9, 30);
		Student staticStudent = new Student(id, new User(2), beginDatum, endDatum, "" + 1743484, new Course(1));
		String query = "SELECT * FROM student";
		long beforeCounter = 0;
		long afterCounter = 0;

		beforeCounter = student_rep.count();
		//Id zurückbekommen
		id = student.addRole(staticStudent);

		afterCounter = student_rep.count();
		
		Student functionStudent = null;
		//Objekt mit Id aufrufen
		functionStudent = (Student) student.getRoleById(id);
		assertEquals(afterCounter, beforeCounter + 1);
		assertEquals(staticStudent.getCourse(), functionStudent.getCourse());
		assertEquals(staticStudent.getEnrollmentNumber(), functionStudent.getEnrollmentNumber());
		assertEquals(staticStudent.getUser(), functionStudent.getUser());
		assertEquals(staticStudent.getEndDate(), functionStudent.getEndDate());
		assertEquals(staticStudent.getStartDate(), functionStudent.getStartDate());
	}

	@Test
	public void testEditRoleSuccess() {
		int id = 34;
		Date beginDatum = new Date(2012, 10, 01);
		Date endDatum = new Date(2015, 9, 30);
		Student staticStudent = new Student(id, new User(38), beginDatum, endDatum, "" + 1743484, new Course(1));
		student.editRole(staticStudent);
		Student functionStudent = (Student) student.getRoleById(34);
		assertEquals(staticStudent.getCourse(), functionStudent.getCourse());
		assertEquals(staticStudent.getEnrollmentNumber(), functionStudent.getEnrollmentNumber());
		assertEquals(staticStudent.getUser(), functionStudent.getUser());
		assertEquals(staticStudent.getEndDate(), functionStudent.getEndDate());
		assertEquals(staticStudent.getStartDate(), functionStudent.getStartDate());
		assertEquals(staticStudent.getId(), functionStudent.getId());
	}

	@Test(expected = RuntimeException.class)
	public void testEditRoleFail() {
		Student functionStudent = (Student) student.getRoleById(3);
		functionStudent.setId(232323);
		student.editRole(functionStudent);
	}

	@Test
	public void testDeleteRoleByIDSuccess() {
		student.deleteRoleById(2);
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -1);
		Date yesterday = new java.sql.Date(cal.getTimeInMillis());
		assertEquals(yesterday, student.getRoleById(2).getEndDate());
	}

	@Test(expected = RuntimeException.class)
	public void testDeleteRoleByIdFail() {
		student.deleteRoleById(4372728);
	}

}
