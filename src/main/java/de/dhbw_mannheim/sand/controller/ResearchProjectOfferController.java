package de.dhbw_mannheim.sand.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import de.dhbw_mannheim.sand.model.ResearchProjectOffer;
import de.dhbw_mannheim.sand.model.Thread;
import de.dhbw_mannheim.sand.model.User;
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
	@RequestMapping(method = RequestMethod.GET, value = "/")
	@ResponseBody
	public ResponseEntity<List<ResearchProjectOffer>> getAll(
		@RequestHeader(value="authorization", defaultValue="X") String authorization ){
        List<ResearchProjectOffer> offers = service.getAllProjects();
        for(ResearchProjectOffer rpo:offers){
        	modifyRPO(rpo);
        }
        return new ResponseEntity<List<ResearchProjectOffer>>(offers, HttpStatus.OK);  
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
		@RequestHeader(value="authorization", defaultValue="X") String authorization, @PathVariable(value = "id") int id) {
		try {
			ResearchProjectOffer offer = service.getProjectById(id);
            if (offer != null) {
            	modifyRPO(offer);
                return new ResponseEntity<ResearchProjectOffer>(offer, HttpStatus.OK);
            } 
			return new ResponseEntity<ResearchProjectOffer>(HttpStatus.NOT_FOUND);
		} 
		catch (Exception e) {
			return new ResponseEntity<ResearchProjectOffer>(HttpStatus.INTERNAL_SERVER_ERROR);
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
            @RequestHeader(value="authorization", defaultValue="X") String authorization, @PathVariable(value = "uuid") String uuid) {
		try {
			ResearchProjectOffer offer = service.getProjectByUuid(uuid+"");
			modifyRPO(offer);
            return new ResponseEntity<ResearchProjectOffer>(offer, HttpStatus.OK);
      		} 
		catch (Exception e) {
			return new ResponseEntity<ResearchProjectOffer>(HttpStatus.INTERNAL_SERVER_ERROR);
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
    @RequestMapping(method=RequestMethod.POST, value = "/")
    public ResponseEntity<ResearchProjectOffer> add( 
    	@RequestHeader(value="authorization", defaultValue="X") String authorization, 
    	@RequestBody ResearchProjectOffer offer) {
    	try {
    		int id = service.addProject(offer);
        	ResearchProjectOffer createdOffer = service.getProjectById(id);;
        	modifyRPO(createdOffer);
        	return new ResponseEntity<ResearchProjectOffer>(createdOffer, HttpStatus.OK);
    	}
    	catch (Exception e) {
    		return new ResponseEntity<ResearchProjectOffer>(HttpStatus.INTERNAL_SERVER_ERROR);
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
    @RequestMapping(method=RequestMethod.PUT, value = "/")
    public ResponseEntity<ResearchProjectOffer> edit(
    	@RequestHeader(value="authorization", defaultValue="X") String authorization, 
    	@RequestBody ResearchProjectOffer offer) {
    	try {
    		service.editProject(offer);
    		ResearchProjectOffer editedRPO = service.getProjectById(offer.getId());
    		modifyRPO(editedRPO);
    		return new ResponseEntity<ResearchProjectOffer>(editedRPO, HttpStatus.OK);
    	}
    	catch (Exception e) {
    		return new ResponseEntity<ResearchProjectOffer>(HttpStatus.INTERNAL_SERVER_ERROR);
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
	@RequestMapping(method=RequestMethod.DELETE, value = "/{id}")
	public ResponseEntity<ResearchProjectOffer> delete(
		@RequestHeader(value="authorization", defaultValue="X") String authorization, 
		@PathVariable(value = "id") int id) {
		try {
			service.deleteProjectById(id);
			return new ResponseEntity<ResearchProjectOffer>(HttpStatus.OK);
		}
		catch (Exception e) {
			return new ResponseEntity<ResearchProjectOffer>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
        
    private void modifyRPO(ResearchProjectOffer rpo) {
		List<User> users = new ArrayList<>();
		for(User user: rpo.getUsers()) {
			users.add(new User(user.getId()));
		}
		rpo.setUsers(users);
		List<Thread> threads = new ArrayList<>();
		for(Thread thread: rpo.getThreads()) {
			threads.add(new Thread(thread.getId()));
		}
		rpo.setThreads(threads);
		rpo.setCreator(new User(rpo.getCreator().getId()));
    }
}
