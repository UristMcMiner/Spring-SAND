package de.dhbw_mannheim.sand.model;

import java.util.Date;
import java.util.UUID;


public class Token {

	private UUID id;
	private User user;
	private Date endDate;


	public Token(User user) {
		this.user = user;
	}

	public Token(UUID id, User user, Date endDate) {
		this.id = id;
		this.user = user;
		this.endDate = endDate;
	}

	public UUID getId() {
		return id;
	}

	public String getIdString() {
		return id.toString();
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

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public boolean isValid() {
		Date now = new Date();

		if (getEndDate().after(now)) {
			return true;
		}
		return false;
	}

}
