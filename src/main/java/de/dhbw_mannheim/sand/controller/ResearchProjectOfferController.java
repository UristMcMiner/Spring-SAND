package de.dhbw_mannheim.sand.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import de.dhbw_mannheim.sand.model.ResearchProjectOffer;
import de.dhbw_mannheim.sand.service.ResearchProjectOfferService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@CrossOrigin
@RestController
@RequestMapping(value = "/sand/researchprojectoffer")
public class ResearchProjectOfferController {
	
	@Autowired
	private ResearchProjectOfferService service;

	/**
	 * REST-Endpoint for getting all ResearchProjectOffers.
	 *
         * @param authorization
	 * @return All ResearchProjectPapers
	 *
	 */
//	@Prototype
	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public List<ResearchProjectOffer> getAll(@RequestHeader(value="authorization", defaultValue="X") String authorization ){
            List<ResearchProjectOffer> offers = service.getAllProjects();
            return offers;   
	}
        
	/**
	 * REST-Endpoint for getting the ResearchProjectOffers with the id "id".
         * 
         * @param authorization
	 * @param id
	 * @return ResearchProjectOffers with id "id"
	 *
	 */
        @RequestMapping(method = RequestMethod.GET, value = "/{id}")
        @ResponseBody
        public ResponseEntity<ResearchProjectOffer> getById(
                        @RequestHeader(value="authorization", defaultValue="X") String authorization, 
			@PathVariable(value = "id") int id) {
		try {
			ResearchProjectOffer offer = service.getProjectById(id);
                        if (offer != null) {
                            return new ResponseEntity<>(offer, HttpStatus.OK);
                        } 
			
                        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
        
        /**
	 * REST-Endpoint for getting the ResearchProjectOffers with the uuid "uuid".
         * 
         * @param authorization
	 * @param uuid
	 * @return ResearchProjectOffers with uuid "uuid"
	 *
	 */
        @RequestMapping(method = RequestMethod.GET, value = "/private/{uuid}")
        @ResponseBody
        public ResponseEntity<ResearchProjectOffer> getByUuid(
                        @RequestHeader(value="authorization", defaultValue="X") String authorization, 
			@PathVariable(value = "uuid") String uuid) {
		try {
			ResearchProjectOffer offer = service.getProjectByUuid(uuid+"");
                        return new ResponseEntity<>(offer, HttpStatus.OK);
      		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
        
	/**
	 * REST-Endpoint for creating a new ResearchProjectOffer
	 *
         * @param authorization
         * @param offer
	 * @return The created ResearchProjectOffer
	 *
	 */
        @RequestMapping(method=RequestMethod.POST)
        public ResponseEntity<ResearchProjectOffer> add( 
                @RequestHeader(value="authorization", defaultValue="X") String authorization, 
                @RequestBody ResearchProjectOffer offer) {
		try {
                    int id = service.addProject(offer);
                    ResearchProjectOffer createdOffer = service.getProjectById(id);;
                    return new ResponseEntity<>(createdOffer, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
        
        /**
	 * REST-Endpoint for editing ResearchProjectOffer
	 *
         * @param authorization
         * @param offer
	 * @return The created ResearchProjectOffer
	 *
	 */
        @RequestMapping(method=RequestMethod.PUT)
        public ResponseEntity<ResearchProjectOffer> edit(
                @RequestHeader(value="authorization", defaultValue="X") String authorization, 
                @RequestBody ResearchProjectOffer offer) {
		try {
                    service.editProject(offer);
                    return new ResponseEntity<>(service.getProjectById(offer.getId()), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
        
       /**
	 * REST-Endpoint for deleting an exisiting ResearchProjectOffer
	 *
         * @param authorization
	 * @param id
         * @return 
	 *
	 */
        @RequestMapping(method=RequestMethod.DELETE)
        public ResponseEntity<ResearchProjectOffer> delete(
                @RequestHeader(value="authorization", defaultValue="X") String authorization, 
                @PathVariable(value = "id") int id) {
		try {
                    service.deleteProjectById(id);
                    return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
