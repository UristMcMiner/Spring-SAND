package de.dhbw_mannheim.sand.model;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @toDO write explanation of class here
 */
@MappedSuperclass
public abstract class LazyObject {
	// @JsonSerialize(using = LazyObjectSerializer.class)

	@JsonProperty("id")
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id = null;

	@JsonIgnore
	@Transient
	private boolean isLoaded;

	@JsonCreator
	public LazyObject(@JsonProperty("id") Integer id) {
		setId(id);
	}

	@JsonProperty("id")
	public Integer getId() {
		return id;
	}

	@JsonProperty("id")
	public void setId(Integer id) {
		if (id == null) {
			this.id = -1;
		} else {
			this.id = id;
		}
	}

	protected LazyObject() {
		id = null;
	}

	@JsonIgnore
	public boolean getIsLoaded() {
		return isLoaded;
	}

	@JsonIgnore
	public void setIsLoaded(boolean isLoaded) {
		this.isLoaded = isLoaded;
	}

	// TODO: Load method

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}

		if (obj == this) {
			return true;
		}

		if (!(obj instanceof LazyObject)) {
			return false;
		}

		LazyObject that = (LazyObject) obj;

		return this.id == that.id;
	}

	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return super.hashCode();
	}

}
