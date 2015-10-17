package de.dhbw_mannheim.sand.service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.dhbw_mannheim.sand.model.Session;

@Service
public class SessionServiceImpl implements SessionService {

	private static final Logger logger = LogManager.getLogger(SessionServiceImpl.class);

	@Autowired
	private UserService userService;

	private Map<UUID, Session> sessions = new HashMap<>();

	@Override
	public UUID addSession(Session session) {
		session.setId(UUID.randomUUID());
		logger.debug("Adding session for user " + session.getUser().getLogin() + " with ID " + session.getId());
		sessions.put(session.getId(), session);
		return session.getId();
	}

	@Override
	public Session getSessionById(UUID id) {
		Session session = sessions.get(id);

		if (session == null) {
			return null;
		}

		session.setUser(userService.getUserById(session.getUser().getId()));
		return session;
	}

	@Override
	public void deleteSessionById(UUID id) {
		logger.debug("Deleting session with ID " + id);
		sessions.remove(id);
	}

	@Override
	public void editSession(Session session) {
		sessions.put(session.getId(), session);
	}

}
