package de.dhbw_mannheim.sand.service;

import java.util.UUID;

import de.dhbw_mannheim.sand.model.Session;

public interface SessionService {

	public UUID addSession(Session session);

	public Session getSessionById(UUID id);

	public void deleteSessionById(UUID id);

	public void editSession(Session session);
}
