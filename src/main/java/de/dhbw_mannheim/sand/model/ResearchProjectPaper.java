package de.dhbw_mannheim.sand.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;

@Entity
@Table(name="research_project_paper")
@PrimaryKeyJoinColumn(name="research_project_id")
@JsonAutoDetect
@JsonInclude(value=Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeName("paper")
public class ResearchProjectPaper extends ResearchProject {


	@ManyToOne
	@JoinColumn(name="teacher_id")
	private Teacher teacher;

	@ManyToOne
	@JoinColumn(name="course_id")
	private Course course;

	private Boolean pending;

	@ManyToMany
	@JoinTable
		(name="research_project_paper_has_student",
		joinColumns = @JoinColumn(name="research_project_paper_id"),
		inverseJoinColumns = @JoinColumn(name="student_id"))
	private List<Student> students;
	
	protected ResearchProjectPaper() {
		super();
	}

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
	
	public void delete() {
		super.setDeleted(1);
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
