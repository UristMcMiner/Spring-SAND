package de.dhbw_mannheim.sand.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@MappedSuperclass
//@Entity
//@Inheritance(strategy=InheritanceType.JOINED)
//@DiscriminatorColumn(discriminatorType=DiscriminatorType.INTEGER, name="dType")
//@Table(name="roles")
@JsonInclude(value=Include.NON_NULL)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({ @JsonSubTypes.Type(value = Secretary.class), @JsonSubTypes.Type(value = Student.class),
		@JsonSubTypes.Type(value = Supervisor.class), @JsonSubTypes.Type(value = Teacher.class),
		@JsonSubTypes.Type(value = Admin.class) })
public abstract class Role extends LazyObject {

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="user_id")
	private User user;

	@Temporal(TemporalType.DATE)
	@Column(name="start_date")
	private Date startDate;

	@Temporal(TemporalType.DATE)
	@Column(name="end_date")
	private Date endDate;

	// private String type;

	public Role(int id) {
		super(id);
		setIsLoaded(false);
	}

	@JsonCreator
	public Role(@JsonProperty("id") Integer id, @JsonProperty("user") User user,
			@JsonProperty("startDate") Date startDate,
			@JsonProperty("endDate") Date endDate) {
		super(id);
		this.setUser(user);
		this.startDate = startDate;
		this.endDate = endDate;
		setIsLoaded(true);
	}

	public Date getStartDate() {
		return this.startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return this.endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@JsonIgnore
	public boolean isValid() {
		Date now = new Date();

		if (getStartDate().before(now) && (endDate == null || getEndDate().after(now))) {
			return true;
		}
		return false;
	}
	
	@Override
	@GeneratedValue
	public int getId(){
		return super.getId();
	}

	// public String getType() {
	// return type;
	// }
	//
	// public void setType(String type) {
	// this.type = type;
	// }

}
