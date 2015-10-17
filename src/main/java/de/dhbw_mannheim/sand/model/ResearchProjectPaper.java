package de.dhbw_mannheim.sand.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;


@JsonAutoDetect
@JsonInclude(value=Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeName("paper")
public class ResearchProjectPaper extends ResearchProject {

	private Teacher teacher;

	private Course course;

	private Boolean pending;

	private List<Student> students;

	public ResearchProjectPaper(int id) {
		super(id);

		setIsLoaded(false);
	}

	@JsonCreator
	public ResearchProjectPaper(@JsonProperty("id") Integer id, @JsonProperty("title") String title,
			@JsonProperty("creator") User creator, @JsonProperty("description") String description,
			@JsonProperty("descriptionLong") String descriptionLong, @JsonProperty("teacher") Teacher teacher,
			@JsonProperty("course") Course course, @JsonProperty("pending") Boolean pending,
			@JsonProperty("students") List<Student> students, @JsonProperty("threads") List<Thread> threads) {
		super(id, title, creator, description, descriptionLong, threads);

		this.teacher = teacher;
		this.course = course;
		this.pending = pending;
		this.students = students;
		setIsLoaded(true);
	}

	public Teacher getTeacher() {
		return teacher;
	}

	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	public Boolean isPending() {
		return pending;
	}

	public void setPending(Boolean pending) {
		this.pending = pending;
	}

	public List<Student> getStudents() {
		return students;
	}

	public void setStudents(List<Student> students) {
		this.students = students;
	}

	public boolean isUserParticipatingOnPaper(int userId) {
		for (Student student : this.getStudents()) {
			if (student.getUser().getId() == userId) {
				return true;
			}
		}
		return false;
	}
}
