package de.dhbw_mannheim.sand.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;

@Entity
@DiscriminatorValue(value="0")
@Table(name="student")
@JsonAutoDetect
@JsonInclude(value=Include.NON_NULL)
@JsonTypeName("student")
/*
 * The fronted uses this id internally but filtering it out on the client side is rather tricky so we simply exclude it
 * herel
 */
@JsonIgnoreProperties({ "typedId" })
public class Student extends Role {

	@Column(name="enrollment_number")
	private String enrollmentNumber;

	@ManyToOne
	@JoinColumn(name="course_id")
	private Course course;

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	protected Student() {
		super(-1);
		this.setId(null);
		setIsLoaded(false);
	}
	
	public Student(int id) {
		super(id);
		setIsLoaded(false);
	}

	@JsonCreator
	public Student(@JsonProperty("id") Integer id, @JsonProperty("user") User user,
			@JsonProperty("startDate") Date startDate,
			@JsonProperty("endDate") Date endDate, @JsonProperty("enrollmentNumber") String enrollmentNumber
//			, @JsonProperty("course" ) Course course
			)
			{
		super(id, user, startDate, endDate);

		this.enrollmentNumber = enrollmentNumber;
//		this.course = course;
		setIsLoaded(true);
	}

	public String getEnrollmentNumber() {
		return this.enrollmentNumber;
	}

	public void setEnrollmentNumber(String enrollmentNumber) {
		this.enrollmentNumber = enrollmentNumber;
	}
	
	@Override
	@GeneratedValue(strategy=GenerationType.AUTO)
	public int getId(){
		return super.getId();
	}


}
