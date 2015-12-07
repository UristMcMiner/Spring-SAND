package de.dhbw_mannheim.sand.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonAutoDetect
@JsonInclude(value = Include.NON_NULL)
public class Event extends LazyObject {

	@JsonCreator
	public Event(@JsonProperty("id") Integer id) {
		super(id);
	}

}
