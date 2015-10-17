package de.dhbw_mannheim.sand.model;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name="course")
@JsonAutoDetect
@JsonInclude(value=Include.NON_NULL)
public class Course extends LazyObject {

	@Basic(fetch=FetchType.LAZY)
	private String name;

	@Transient
	private Supervisor supervisor;

	@Transient
	private Secretary secretary;

	protected Course() {
		super();
	}
	
	public Course(int id) {
		super(id);
		setIsLoaded(false);
	}

	@JsonCreator
	public Course(@JsonProperty("id") Integer id, @JsonProperty("name") String name,
			@JsonProperty("supervisor") Supervisor supervisor, @JsonProperty("secretary") Secretary secretary) {
		super(id);
		this.name = name;
		this.supervisor = supervisor;
		this.secretary = secretary;
		setIsLoaded(true);
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Supervisor getSupervisor() {
		return this.supervisor;
	}

	public void setSupervisor(Supervisor supervisor) {
		this.supervisor = supervisor;
	}

	public Secretary getSecretary() {
		return this.secretary;
	}

	public void setSecretary(Secretary secretary) {
		this.secretary = secretary;
	}

}
