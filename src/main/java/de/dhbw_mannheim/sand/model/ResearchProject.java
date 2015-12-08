package de.dhbw_mannheim.sand.model;

import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@Entity
@Inheritance(strategy=InheritanceType.JOINED)
@Table(name="research_project")
@JsonAutoDetect
@JsonInclude(value=Include.NON_NULL)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({ @JsonSubTypes.Type(value = ResearchProjectOffer.class),
		@JsonSubTypes.Type(value = ResearchProjectPaper.class) })
public class ResearchProject extends LazyObject {

	@Basic
	private String title;

	@ManyToOne
	@JoinColumn(name="creator_user_id")
	private User creator;

	@Basic
	private String description;

	@Basic
	@Column(name="description_long")
	private String descriptionLong;

	@OneToMany(mappedBy="researchProject")
	private List<Thread> threads;

	@JsonIgnore
	private int deleted;
	
	@JsonCreator
	public ResearchProject(@JsonProperty("id") Integer id) {
		super(id);
		setIsLoaded(false);
	}

	public ResearchProject(Integer id, String title, User creator, String description, String descriptionLong,
			List<Thread> threads) {
		super(id);
		this.title = title;
		this.creator = creator;
		this.description = description;
		this.descriptionLong = descriptionLong;
		this.threads = threads;
		setIsLoaded(true);
	}

	protected ResearchProject() {
		super();
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDescriptionLong() {
		return descriptionLong;
	}

	public void setDescriptionLong(String descriptionLong) {
		this.descriptionLong = descriptionLong;
	}

	public List<Thread> getThreads() {
		return threads;
	}

	public void setThreads(List<Thread> threads) {
		this.threads = threads;
	}

	public User getCreator() {
		return creator;
	}

	public void setCreator(User creator) {
		this.creator = creator;
	}

	public int getDeleted() {
		return deleted;
	}

	public void setDeleted(int deleted) {
		this.deleted = deleted;
	}
}
