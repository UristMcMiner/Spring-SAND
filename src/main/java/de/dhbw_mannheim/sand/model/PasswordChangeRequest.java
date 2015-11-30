package de.dhbw_mannheim.sand.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;


@JsonAutoDetect
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class PasswordChangeRequest {

	private User user;

	private String oldPassword;

	private String newPassword;

	@JsonCreator
	public PasswordChangeRequest(@JsonProperty("user") User user,
			@JsonProperty("oldPassword") String oldPassword,
			@JsonProperty("newPassword") String newPassword) {
		this.setUser(user);
		this.setOldPassword(oldPassword);
		this.setNewPassword(newPassword);
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

}
