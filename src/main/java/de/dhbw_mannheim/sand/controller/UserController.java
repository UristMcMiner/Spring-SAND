package de.dhbw_mannheim.sand.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import de.dhbw_mannheim.sand.annotations.LoggedIn;
import de.dhbw_mannheim.sand.model.PasswordChangeRequest;
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

	
	/**
	 * REST-Endpoint for getting all Users. The result is JSON-formated
	 *
	 * @return Users as JSON
	 *
	 */
	
//	@LoggedIn
	@RequestMapping(method = RequestMethod.GET, value = "/")
//	@ResponseBody
	public ResponseEntity<List<User>> getAll(@RequestHeader(value="authorization", defaultValue="X") String authorization) {
		System.out.println(authorization);
		List<User> allUsers = null;
		if((allUsers = service.getAllUsers()) != null) {
			return new ResponseEntity<List<User>>(allUsers, HttpStatus.OK);
		}
		else{
			return new ResponseEntity<List<User>>(HttpStatus.NOT_FOUND);
		}
	}
	
	/**
	 * REST-Endpoint for getting the User with the id "id". The result is JSON-formated.
	 *
	 * @param int id
	 * @return User with id "id" as JSON
	 *
	 */
	
//	@LoggedIn
	@RequestMapping(method = RequestMethod.GET, value = "/{id}")
//	@ResponseBody
	public ResponseEntity<User> getById(@RequestHeader(value="authorization", defaultValue="X") String authorization, @PathVariable(value = "id") int id) {
		System.out.println(authorization + " " + id);
		User user = null;
		if ((user = service.getUserById(id)) != null) {
			return new ResponseEntity<User>(user, HttpStatus.OK);
		} 
		else {
			return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
		}
	}
	
	/**
	 * REST-Endpoint for adding an User.
	 *
	 * @param User user
	 * @return added User
	 *
	 */

//	@LoggedIn
	@RequestMapping(method = RequestMethod.POST, value="/")
//	@ResponseBody
	public ResponseEntity<User> add(@RequestHeader(value="authorization", defaultValue="X") String authorization, @RequestBody User user) {
		System.out.println(authorization + " " + user.getLogin());
		if(service.hasUserWithLogin(user.getLogin())) {
			return new ResponseEntity<User>(HttpStatus.CONFLICT);
		}
		int id = service.addUser(user);
		return new ResponseEntity<User>(service.getUserById(id) , HttpStatus.CREATED);
	}
	
	/**
	 * REST-Endpoint for editing an User. NOT TESTED SUCCESSFULLY YET
	 *
	 * @param User user
	 * @return edited User
	 *
	 */
	
//	@LoggedIn
	@RequestMapping(method = RequestMethod.PUT, value = "/")
//	@ResponseBody
	public ResponseEntity<User> edit(@RequestHeader(value="authorization", defaultValue="X") String authorization, @RequestBody User user) {
		System.out.println(authorization + " " + user.getId());
		User oldUser = service.getUserById(user.getId());
		if (!oldUser.getLogin().equals(user.getLogin())) {
			if (service.hasUserWithLogin(user.getLogin())) {
				return new ResponseEntity<User>(HttpStatus.CONFLICT);
			}
		}
		try{
			service.editUser(user);
			return new ResponseEntity<User>(service.getUserById(user.getId()), HttpStatus.OK);
		}
		catch(RuntimeException e) {
			return new ResponseEntity<User>(HttpStatus.CONFLICT);
		}
	}
	
	/**
	 * REST-Endpoint for deleting an User.
	 *
	 * @param int id
	 * @return String with value "true".
	 *
	 */
	
//	@LoggedIn
	@RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
//	@ResponseBody
	public ResponseEntity<String> delete(@RequestHeader(value="authorization", defaultValue="X") String authorization, @PathVariable(value = "id") int id) {
		System.out.println(authorization + " " + id);
		try{
			service.deleteUserById(id);
			return new ResponseEntity<String>("true", HttpStatus.OK);
		}
		catch(RuntimeException e) {
			return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
		}
	}
	
	/**
	 * REST-Endpoint for changing an User's password
	 *
	 * @param PasswordChangeRequest passwordChangeRequest
	 * @return PasswordChangeRequest passwordChangeRequest
	 *
	 */
	
	//@LoggedIn
	@RequestMapping(method = RequestMethod.POST, value = "/password")
	//@ResponseBody
	public ResponseEntity<PasswordChangeRequest> changePassword(@RequestHeader(value="authorization", defaultValue="X") String authorization, @RequestBody PasswordChangeRequest passwordChangeRequest) {
		System.out.println(authorization + " " + passwordChangeRequest.getUser().getId() + " " + passwordChangeRequest.getOldPassword() + " " + passwordChangeRequest.getNewPassword());
		
		User user = service.getUserById(passwordChangeRequest.getUser().getId());
		if(user == null) {
			return new ResponseEntity<PasswordChangeRequest>(HttpStatus.NOT_FOUND);
		}
		user.setPassword(passwordChangeRequest.getNewPassword());
		service.changePassword(user);
		return new ResponseEntity<PasswordChangeRequest>(passwordChangeRequest, HttpStatus.OK);
	}
}
