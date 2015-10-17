package de.dhbw_mannheim.sand.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonAutoDetect
@JsonInclude(value=Include.NON_NULL)
@JsonTypeName("secretary")
/*
 * The fronted uses this id internally but filtering it out on the client side is rather tricky so we simply exclude it
 * here
 */
@JsonIgnoreProperties({ "typedId" })
public class Secretary extends Role {

//	private Course course;

	private String phone;

	private String room;

	public Secretary(int id) {
		super(id);
		setIsLoaded(false);
	}

	@JsonCreator
	public Secretary(@JsonProperty("id") Integer id, @JsonProperty("user") User user,
			@JsonProperty("startDate") Date startDate, @JsonProperty("endDate") Date endDate,
//			@JsonProperty("course") Course course, 
			@JsonProperty("phone") String phone,
			@JsonProperty("room") String room) {
		super(id, user, startDate, endDate);

//		this.course = course;
		this.phone = phone;
		this.room = room;
		setIsLoaded(true);
	}

	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getRoom() {
		return this.room;
	}

	public void setRoom(String room) {
		this.room = room;
	}
}
