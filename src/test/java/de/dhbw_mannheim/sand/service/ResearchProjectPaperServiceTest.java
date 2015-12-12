package de.dhbw_mannheim.sand.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import de.dhbw_mannheim.sand.SAND;
import de.dhbw_mannheim.sand.model.Course;
import de.dhbw_mannheim.sand.model.ResearchProjectPaper;
import de.dhbw_mannheim.sand.model.Student;
import de.dhbw_mannheim.sand.model.Teacher;
import de.dhbw_mannheim.sand.model.User;
import de.dhbw_mannheim.sand.repository.ResearchProjectPaperRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes=SAND.class)
@Transactional
@Rollback(value=true)
public class ResearchProjectPaperServiceTest  {

	@Autowired
	private ResearchProjectPaperService paper_service;
	@Autowired
	private ResearchProjectPaperRepository paper_rep;

	@Test
	public void testGetAllProjects() {
		// Test get all project from research_project_paper table
		List<ResearchProjectPaper> resultResearchProjectPaperList = paper_service.getAllProjects();
		int count = (int)paper_rep.count();
		assertEquals(count, resultResearchProjectPaperList.size()); // amount of projects
	}

	@Test
	public void testGetProjectById() {
		// Test NaN id parameter
		int id = 0;
		ResearchProjectPaper resultResearchProjectPaper = paper_service.getProjectByID(id);
		assertEquals(null, resultResearchProjectPaper);

		// Test not existing id parameter
		id = 99999999;
		resultResearchProjectPaper = paper_service.getProjectByID(id);
		assertEquals(null, resultResearchProjectPaper);

		// Test existing id parameter
		id = 9;
		resultResearchProjectPaper = paper_service.getProjectByID(id);
		assertEquals("BESCHREIBUNG KURZ 9", resultResearchProjectPaper.getDescription());
	}


	@Test
	public void testAddProject() {
		int paperId = 82973;
		int creatorId = 1;
		int teacherId = 1;
		int studentId = 1;
		int userId = 5; //Student with id=1 is user with id=5
		int courseId = 1;

		String desc = "Beschreibung";
		String descLong = "Beschreibung Lang";
		String title = "Neuer Titel";

		User creator = new User(creatorId);
		Teacher teacher = new Teacher(teacherId);
		Student student = new Student(studentId);

		User sUser = new User(userId);
		student.setUser(sUser);
		List<Student> students = new ArrayList<>();
		students.add(student);

		Course course = new Course(courseId);

		ResearchProjectPaper project = new ResearchProjectPaper(paperId);
		project.setTeacher(teacher);
		project.setCreator(creator);
		project.setCourse(course);
		project.setStudents(students);
		project.setDescription(desc);
		project.setDescriptionLong(descLong);
		project.setTitle(title);

		int old = (int)paper_rep.count();
		//Unknown SQL exception?
		int test = paper_service.addProjectPaper(project);
		
		int count = (int)paper_rep.count();
		assertEquals(old+1, count);
	}


	@Test
	public void testEditProject(){
		int paperId = 1;
		int creatorId = 1;
		int teacherId = 1;
		int studentId = 1;
		int courseId = 1;

		String desc = "Beschreibung";
		String descLong = "Beschreibung Lang";
		String title = "Neuer Titel";

		User creator = new User(creatorId);
		Teacher teacher = new Teacher(teacherId);
		Student student = new Student(studentId);

		List<Student> students = new ArrayList<>();
		students.add(student);

		Course course = new Course(courseId);

		ResearchProjectPaper expected = new ResearchProjectPaper(paperId);
		expected.setTeacher(teacher);
		expected.setCreator(creator);
		expected.setCourse(course);
		expected.setStudents(students);
		expected.setDescription(desc);
		expected.setDescriptionLong(descLong);
		expected.setTitle(title);
		expected.setPending(false);

		//paper exists
		paper_service.editProject(expected);
		ResearchProjectPaper actual = paper_rep.findOne(paperId);

		assertEquals(expected.getTitle(), actual.getTitle());
		assertEquals(expected.getDescription(), actual.getDescription());
		assertEquals(expected.getDescriptionLong(), actual.getDescriptionLong());
		assertEquals(expected.getCourse().getId(), actual.getCourse().getId());
		assertEquals(expected.getCreator().getId(), actual.getCreator().getId());
		assertEquals(expected.getStudents().size(), actual.getStudents().size());

		//KEIN TEST? WAS SOLL DAS?
//		//offer exists
//		students.clear();
//		int id = 2;
//		creatorId = 6;
//		desc = "BESCHREIBUNG KURZ 2";
//		descLong = "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed  consetetur sadipscing elitr, sed diam! Lorem ipsum dolor sit amet, diam! Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam! 2";
//		title = "Irgend ein Thema, dem Bedeutung beigemessen wird";
//
//		int u1Id = 13;
//		int u2Id = 42;
//		User u1 = new User(u1Id);
//		User u2 = new User(u2Id);
//
//		int s1Id = 9; // user 13
//		int s2Id = 38; // user 42
//
//		Student s1 = new Student(s1Id);
//		Student s2 = new Student(s2Id);
//		s1.setUser(u1);
//		s2.setUser(u2);
//
//		students.add(s1);
//		students.add(s2);
//
//		expected.setId(id);
//		expected.setDescription(desc);
//		expected.setDescriptionLong(descLong);
//		expected.setTitle(title);
//		expected.setStudents(students);
//
//		paper_service.editProject(expected);


	}
	@Test
	public void testDeleteProjectByID() {
		int id = 1;
		//@formatter:off
		/**
		// Test NaN id parameter
		int id = (int) Float.NaN;
		service.deleteProjectById(id);
		fail();

		// Test not existing id parameter
		id = 99999999;
		service.deleteProjectById(id);
		fail();
		*/
		//@formatter:on

		// Test existing id parameter
		ResearchProjectPaper paper = paper_rep.findOne(id);
		paper_service.deleteProjectPaper(paper);
		paper = paper_rep.findOne(id);
		ArrayList<ResearchProjectPaper> papers = new ArrayList<ResearchProjectPaper>();
		if(paper != null)
		papers.add(paper);
		assertEquals(true, papers.isEmpty());
		
	}

//	@Test
//	public void testGetProjectByUserID() {
//		// Test NaN id parameter
//		int user_id = (int) Float.NaN;
//		List<ResearchProjectPaper> resultResearchProjectPaperList = paper_service.getProjectsByUserId(user_id);
//		assertTrue(resultResearchProjectPaperList.isEmpty());
//
//		// Test not existing id parameter
//		user_id = 99999999;
//		resultResearchProjectPaperList = paper_service.getProjectsByUserId(user_id);
//		assertTrue(resultResearchProjectPaperList.isEmpty());
//
//		// Test existing id parameter
//		user_id = 9;
//		resultResearchProjectPaperList = paper_service.getProjectsByUserId(user_id);
//		assertEquals(1, resultResearchProjectPaperList.size());
//	}

//	@Test
//	public void testGetProjectsByUserId() {
//		int paperId = 13;
//		int userId = 43;
//		int creatorId = 33;
//		int teacherId = 1;
//		int courseId = 2;
//		String title = "Employer Branding und Social Media";
//		String desc = "BESCHREIBUNG KURZ 13";
//		String descLong = "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam! Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam! Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam! 13";
//		User creator = new User(creatorId);
//		Teacher teacher = new Teacher(teacherId);
//		Course course = new Course(courseId);
//
//		ResearchProjectPaper paper = new ResearchProjectPaper(paperId);
//		paper.setTitle(title);
//		paper.setDescription(desc);
//		paper.setDescriptionLong(descLong);
//		paper.setCreator(creator);
//		paper.setTeacher(teacher);
//		paper.setCourse(course);
//		paper.setPending(false);
//
//		List<ResearchProjectPaper> expected = new ArrayList<>();
//		expected.add(paper);
//		List<ResearchProjectPaper> actual = paper_service.getProjectsByUserId(userId);
//
//		assertEquals(expected.size(), actual.size());
//		ResearchProjectPaper paperF = actual.get(0);
//
//		assertEquals(paper.getTitle(), paperF.getTitle());
//		assertEquals(paper.getDescription(), paperF.getDescription());
//		assertEquals(paper.getDescriptionLong(), paperF.getDescriptionLong());
//		assertEquals(paper.getTeacher().getId(), paperF.getTeacher().getId());
//		assertEquals(paper.getCreator().getId(), paperF.getCreator().getId());
//	}
//
//	@Test
//	public void testIsUserParticipatingOnPaper() {
//		int paperId = 13;
//		int userId = 43;
//
//		boolean isParticipating = paper_service.isUserParticipatingOnPaper(paperId, userId);
//		assertEquals(true, isParticipating);
//
//		userId = 1; // There is only user with id=43 on paper with id = 13;
//		isParticipating = true;
//		isParticipating = paper_service.isUserParticipatingOnPaper(paperId, userId);
//		assertEquals(false, isParticipating);
//
//		// if project hast no paper
//		isParticipating = true;
//		isParticipating = paper_service.isUserParticipatingOnPaper(-1, userId);
//		assertEquals(false, isParticipating);
//	}


}
