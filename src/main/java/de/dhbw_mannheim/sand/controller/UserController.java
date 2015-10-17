package de.dhbw_mannheim.sand.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import de.dhbw_mannheim.sand.annotations.LoggedIn;
import de.dhbw_mannheim.sand.model.User;
import de.dhbw_mannheim.sand.repository.UserRepository;
import de.dhbw_mannheim.sand.service.UserService;

@CrossOrigin
@RestController
@RequestMapping(value = "/sand/users")
public class UserController {
	
	@Autowired
	private UserRepository repository;
	
	@Autowired
	private UserService service;

//	@LoggedIn
	@RequestMapping(method = RequestMethod.GET, value = "/{id}")
//	@ResponseBody
	public ResponseEntity<User> getUserById(
			@RequestHeader(value="authorization", defaultValue="X") String authorization, 
			@PathVariable(value = "id") int id) {
		System.out.println(authorization + " " + id);
		User user = null;
		if ((user = service.getUserById(id)) != null) {
			return new ResponseEntity<User>(user, HttpStatus.OK);
		} else {
			return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
		}
	}

}
