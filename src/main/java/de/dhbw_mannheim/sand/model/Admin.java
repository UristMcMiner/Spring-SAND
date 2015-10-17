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
@JsonTypeName("admin")
/*
 * The fronted uses this id internally but filtering it out on the client side is rather tricky so we simply exclude it
 * here
 */
@JsonIgnoreProperties({ "typedId" })
public class Admin extends Role {

	public Admin(int id) {
		super(id);

		setIsLoaded(false);
	}

	@JsonCreator
	public Admin(@JsonProperty("id") Integer id, @JsonProperty("user") User user,
			@JsonProperty("startDate") Date startDate, @JsonProperty("endDate") Date endDate) {
		super(id, user, startDate, endDate);

		setIsLoaded(true);
	}

}
