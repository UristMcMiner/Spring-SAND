package de.dhbw_mannheim.sand.service;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
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
import de.dhbw_mannheim.sand.model.ResearchProjectOffer;
import de.dhbw_mannheim.sand.model.User;
import de.dhbw_mannheim.sand.repository.ResearchProjectOfferRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes=SAND.class)
@Transactional
@Rollback(value=true)
public class ResearchProjectOfferServiceTest {

	@Autowired
	private ResearchProjectOfferService service;
	
	@Autowired
	private ResearchProjectOfferRepository repository;
	
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
	public void testGetAllProjects() {
		long count = repository.count();
		List<ResearchProjectOffer> resultOfferList = service.getAllProjects();
		assertEquals(count, resultOfferList.size());
	}

	@Test
	public void testGetProjectByID() {
		// Test NaN id parameter
		int id = (int) Float.NaN;
		ResearchProjectOffer resultOffer = service.getProjectById(id);
		assertEquals(null, resultOffer);

		// Test not existing id parameter
		id = 99999999;
		resultOffer = service.getProjectById(id);
		assertEquals(null, resultOffer);

		// Test existing id parameter
		id = 10;
		resultOffer = service.getProjectById(id);
		assertEquals(Boolean.valueOf(false), resultOffer.getVisible());
	}



	@Test
	public void testGetProjectByUuId() {
		String uuid = "fa4b5497-029a-11e5-8f22-000c29433838";
		int id = 2;
		int creatorId = 6;
		ResearchProjectOffer expected = new ResearchProjectOffer(id);

		User creator = new User(creatorId);
		expected.setCreator(creator);
		expected.setTitle("Irgend ein Thema, dem Bedeutung beigemessen wird");
		expected.setDescription("BESCHREIBUNG KURZ 2");
		expected.setDescriptionLong("Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed  consetetur sadipscing elitr, sed diam! Lorem ipsum dolor sit amet, diam! Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam! 2");
		expected.setVisible(false);

		ResearchProjectOffer actual = service.getProjectByUuid(uuid);
		assertEquals(expected.getId(), actual.getId());
		assertEquals(expected.getTitle(), actual.getTitle());
		assertEquals(expected.getDescription(), actual.getDescription());
		assertEquals(expected.getDescriptionLong(), actual.getDescriptionLong());
		assertEquals(expected.getVisible(), actual.getVisible());

	}

	@Test
	public void testAddProject() {
		int id = 1;
		int creatorId = 6;
		ResearchProjectOffer project = new ResearchProjectOffer(id);
		project.setTitle("Test Title");
		project.setDescription("Kurze Beschreibung");
		project.setDescriptionLong("Lange Beschreibung");
		project.setUuid("test-uuid");
		project.setVisible(true);
		User creator = new User(creatorId);
		project.setCreator(creator);

		long oldCount = repository.count();
		@SuppressWarnings("unused")
		int key = service.addProject(project);

		long count = repository.count();

		assertEquals(oldCount + 1, count);
	}

	@Test
	public void testEditProject() {
		//@formatter:off
		/**
		// test edit projectOffer
		ResearchProjectOffer researchProjectOffer = null;

		// Test existing id parameter
		User creator = new User(1);
		User user = new User(2);
		List<User> users = new ArrayList<>();

		int id = 11;
		users.add(user);
		researchProjectOffer = new ResearchProjectOffer(11, "ich wurde geaendert", creator, "a test", "a add test",
				false, users, new ArrayList<Thread>());

		service.editProject(researchProjectOffer);
		researchProjectOffer = service.getProjectById(id);

		assertEquals(researchProjectOffer.getTitle(), "ich wurde geaendert");
		/*
		 * Hier noch mehr testen.
		 */
		//@formatter:on

		int id = 2;
		int creatorId = 1;
		int userId = 1;

		String title = "Neuer Titel";
		String desc = "Beschreibung";
		String descLong = "Beschreibung Lang";

		User creator = new User(creatorId);
		User user = new User(userId);
		List<User> users = new ArrayList<>();
		users.add(user);

		ResearchProjectOffer expected = new ResearchProjectOffer(id);
		expected.setTitle(title);
		expected.setDescription(desc);
		expected.setDescriptionLong(descLong);
		expected.setCreator(creator);
		expected.setUuid("test-uuid");
		expected.setVisible(true);
		expected.setUsers(users);

		service.editProject(expected);

		ResearchProjectOffer actual = service.getProjectById(id);

		assertEquals(expected.getCreator().getId(), actual.getCreator().getId());
		assertEquals(expected.getTitle(), actual.getTitle());
		assertEquals(expected.getDescription(), actual.getDescription());
		assertEquals(expected.getDescriptionLong(), actual.getDescriptionLong());

	}

	@Test
	public void testDeleteProject() {
		int id = 2;
		service.deleteProjectById(id);
		ResearchProjectOffer offer = service.getProjectById(id);
		boolean isDeleted = offer==null;

		assertEquals(true, isDeleted);

	}

	@Test
	public void testGetProjectsByUserId() {
		List<ResearchProjectOffer> expected = new ArrayList<>();

		int id = 22;
		int uId = 1;
		int creatorId = 3;
		String title = "Entwicklung eines Eclipse-plugins als Ersatzbetriebssystem";
		String desc = "BESCHREIBUNG KURZ 22";
		String descLong = "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam! Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam! 22";
		String uuId = "f9e74286-f475-11e4-8f22-000c29433838";

		User user = new User(uId);
		User creator = new User(creatorId);
		List<User> users = new ArrayList<>();
		users.add(user);

		ResearchProjectOffer project = new ResearchProjectOffer(id);
		project.setTitle(title);
		project.setDescription(desc);
		project.setDescriptionLong(descLong);
		project.setUuid(uuId);
		project.setUsers(users);
		project.setCreator(creator);
		project.setVisible(true);
		expected.add(project);

		List<ResearchProjectOffer> actual = service.getProjectsByUserId(uId);

		assertEquals(expected.size(), actual.size());

		ResearchProjectOffer o = actual.get(0);

		assertEquals(project.getTitle(), o.getTitle());
		assertEquals(project.getDescription(), o.getDescription());
		assertEquals(project.getDescriptionLong(), o.getDescriptionLong());
		assertEquals(project.getVisible(), o.getVisible());
		assertEquals(project.getCreator().getId(), o.getCreator().getId());
	}
}
