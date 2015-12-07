package de.dhbw_mannheim.sand.model;

import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonAutoDetect
@JsonInclude(value=Include.NON_NULL)
@JsonTypeName("thread")
/*
 * The fronted uses this id internally but filtering it out on the client side is rather tricky so we simply exclude it
 * here
 */
@Entity
@Table(name="thread")
@JsonIgnoreProperties({ "hidden" })
public class Thread extends LazyObject {

	@Basic(fetch=FetchType.LAZY)
	private String title;

	@OneToMany(fetch=FetchType.LAZY, mappedBy="thread")
	private List<Post> posts;


	private int hidden;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="research_project_id")
	private ResearchProject researchProject;

	protected Thread() {
		super();
	}
	
	public Thread(int id) {
		super(id);
		setIsLoaded(false);
	}

	@JsonCreator
	public Thread(@JsonProperty("id") Integer id, @JsonProperty("title") String title,
			@JsonProperty("posts") List<Post> posts, @JsonProperty("researchProject") ResearchProject researchProject) {
		super(id);
		this.title = title;
		this.posts = posts;
		this.researchProject = researchProject;
		setIsLoaded(true);
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<Post> getPosts() {
		return posts;
	}

	public void setPosts(List<Post> posts) {
		this.posts = posts;
	}

	public ResearchProject getResearchProject() {
		return researchProject;
	}

	public void setResearchProject(ResearchProject researchProject) {
		this.researchProject = researchProject;
	}

}
