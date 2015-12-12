package de.dhbw_mannheim.sand.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import de.dhbw_mannheim.sand.model.Thread;



@CrossOrigin
@RestController
@RequestMapping(value = "/sand/thread")
public class ThreadController {
	
	
	@Autowired
	private ThreadService service;
	
	
	// @LoggedIn
	@RequestMapping(method = RequestMethod.GET, value = "/{id}")
	@ResponseBody
	public ResponseEntity<Thread> getById(
	@RequestHeader(value="authorization", defaultValue="X") String authorization, @PathVariable(value = "id") int id) {
	try {
	Thread thread = service.getThreadById(id);
	if (thread != null) {
	return new ResponseEntity<>(thread, HttpStatus.OK);
	}
	
	return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	} catch (Exception e) {
	return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
	}
	
	 
	@RequestMapping(method = RequestMethod.POST, value="/")
	public ResponseEntity<Thread> addThread(
	@RequestHeader(value="authorization", defaultValue="X") String authorization, @RequestBody Thread thread) {
	int id = service.addPost(thread);
	return new ResponseEntity<Thread>(service.getThreadById(id) , HttpStatus.CREATED);
	}
	
	
	@RequestMapping(method = RequestMethod.PUT, value = "/")
	public ResponseEntity<Thread> editThread(
	@RequestHeader(value="authorization", defaultValue="X") String authorization, @RequestBody Thread thread) {
	if (thread.getHidden()) {
	try {
	service.deleteThreadById(thread.getId());
	} catch (RuntimeException re) {
	return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}

	return new ResponseEntity<>(service.getPostById(thread.getId()), HttpStatus.OK);
	}
	
	return new ResponseEntity<>(HttpStatus.FORBIDDEN);
	}
	
	
	
	@RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
	public ResponseEntity<Thread> deletePost(
	@RequestHeader(value="authorization", defaultValue="X") String authorization, @PathVariable(value = "id") int id) {
	return new ResponseEntity<>(HttpStatus.FORBIDDEN);
	}
	
}
