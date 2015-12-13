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
import de.dhbw_mannheim.sand.model.ResearchProjectOffer;
import de.dhbw_mannheim.sand.model.User;
import de.dhbw_mannheim.sand.repository.ResearchProjectOfferRepository;
import de.dhbw_mannheim.sand.service.ResearchProjectOfferService;
import java.util.ArrayList;
import static org.junit.Assert.assertEquals;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes=SAND.class)
@Transactional
@Rollback(value=true)
public class ResearchProjectOfferControllerTest {

	@Autowired
	private ResearchProjectOfferController controller;
	
	@Autowired
	private ResearchProjectOfferRepository repository;
        
        @Autowired
	private ResearchProjectOfferService service;
	
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
	public void testGetAll() {
		long count = repository.count();
		List<ResearchProjectOffer> resultOfferList = controller.getAll("X");
                assertEquals(count, resultOfferList.size());
	}
        
        @Test
	public void testGetById() {
		// Test NaN id parameter
		int id = (int) Float.NaN;
		ResponseEntity<ResearchProjectOffer> responseRpo = controller.getById("X", id);
		assertEquals(HttpStatus.NOT_FOUND, responseRpo.getStatusCode());

		// Test not existing id parameter
		id = 99999999;
		responseRpo = controller.getById("X", id);
		assertEquals(HttpStatus.NOT_FOUND, responseRpo.getStatusCode());

		// Test existing id parameter
		id = 10;
		responseRpo = controller.getById("X", id);
		assertEquals(HttpStatus.OK, responseRpo.getStatusCode());
	}
}
