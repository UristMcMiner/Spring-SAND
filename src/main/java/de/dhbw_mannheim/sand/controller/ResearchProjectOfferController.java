package de.dhbw_mannheim.sand.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import de.dhbw_mannheim.sand.annotations.Prototype;
import de.dhbw_mannheim.sand.model.ResearchProjectOffer;
import de.dhbw_mannheim.sand.repository.ResearchProjectOfferRepository;
import de.dhbw_mannheim.sand.service.UserService;

@CrossOrigin
@RestController
@RequestMapping(value = "/sand/researchprojectoffer")
public class ResearchProjectOfferController {
	
	@Autowired
	private ResearchProjectOfferRepository repository;
	
	@Autowired
	private UserService service;

	@Prototype
	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public List<ResearchProjectOffer> getResearchProjects(
			@RequestHeader(value="authorization", defaultValue="X") String authorization ){
		List<ResearchProjectOffer> offers = repository.findAll();
		return offers;
	}

}
