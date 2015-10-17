package de.dhbw_mannheim.sand.model;

import java.util.UUID;

public class Session {

	private UUID id;
	private User user;

	public Session(UUID id, User user) {
		this.id = id;
		this.user = user;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
