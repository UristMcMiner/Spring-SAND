package de.dhbw_mannheim.sand.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.websocket.server.PathParam;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import de.dhbw_mannheim.sand.model.Login;
import de.dhbw_mannheim.sand.model.ResearchProject;
import de.dhbw_mannheim.sand.model.Role;
import de.dhbw_mannheim.sand.model.Session;
import de.dhbw_mannheim.sand.model.User;
import de.dhbw_mannheim.sand.service.SessionService;
import de.dhbw_mannheim.sand.service.UserService;


@CrossOrigin
@RestController
@RequestMapping(value = "/sand/session")
public class SessionController {

	private static final Logger logger = LogManager.getLogger(SessionController.class);

	@Autowired
	private UserService userService;

	@Autowired
	private SessionService sessionService;

	/**
	 * Gets the session with the given session id.
	 *
	 * @param id
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/{id}")
	public ResponseEntity<Session> getById(@PathParam("id") String idString) {
		UUID id;
		try {
			id = UUID.fromString(idString);
		} catch (IllegalArgumentException e) {
			logger.info("Unable to parse the UUID string "+ idString, e);
			return new ResponseEntity<Session>(HttpStatus.BAD_REQUEST);
		} catch (NullPointerException npe) {
			return new ResponseEntity<Session>(HttpStatus.BAD_REQUEST);
		}
		Session session = sessionService.getSessionById(id);

		if (session != null) {
			return new ResponseEntity<Session>(session, HttpStatus.OK);
		}

		return new ResponseEntity<Session>(HttpStatus.NOT_FOUND);
	}

	/**
	 * Create a session for a user with the provided credentials. Needs the login credentials in a JSON form:
	 *
	 * <code>
	 * {
	 *    "login": "username"
	 *    "password": "password"
	 * }
	 * </code>
	 *
	 * Returns 403 Forbidden, if no matching user can be found. If there is a matching user, a session object will be
	 * returned:
	 *
	 * <code>
	 * {
	 *    "id": "b27375f0-cec4-11e3-9c1a-0800200c9a66",
	 *    "user": { ...user object... }
	 * }
	 * </code>
	 *
	 * @param login
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST )
	public ResponseEntity<Session> login(@RequestBody Login login) {
		System.out.println("User:"+login);
		User user = userService.getUserByLoginAndPassword(login.getLogin(), login.getPassword());

		if (user != null) {
			modifyUser(user);
			Session session = new Session(null, user);
			sessionService.addSession(session);
			
			return new ResponseEntity<Session> (session, HttpStatus.OK);
		}

		return new ResponseEntity<Session>(HttpStatus.FORBIDDEN);
	}

	/*
	 * logout
	 */
	@RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
	public ResponseEntity<Boolean> logout(@PathParam("id") UUID id) {
		Session session = sessionService.getSessionById(id);

		if (session != null) {
			sessionService.deleteSessionById(id);
			return new ResponseEntity<Boolean>(true, HttpStatus.OK);
		}

		return new ResponseEntity<Boolean>(HttpStatus.NOT_FOUND);
	}
	
	private void modifyUser(User user) {
		List<Role> roles = new ArrayList<>();
		for (Role role: user.getRoles()) {
			roles.add(new Role(role.getId()));
		}
		user.setRoles(roles);
		List<ResearchProject> projects = new ArrayList<>();
		for (ResearchProject project: user.getResearchProjects()) {
			projects.add(new ResearchProject(project.getId()));
		}
		user.setResearchProjects(projects);
	}

}
