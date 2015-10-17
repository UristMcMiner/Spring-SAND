package de.dhbw_mannheim.sand.model;

import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
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
@Table(name="research_project_offer")
@PrimaryKeyJoinColumn(name="research_project_id")
@JsonAutoDetect
@JsonInclude(value=Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeName("offer")
public class ResearchProjectOffer extends ResearchProject {

	@Basic
	private Boolean visible;

	@ManyToMany
	@JoinTable
		(name="research_project_offer_has_user",
		joinColumns = @JoinColumn(name="research_project_offer_id"),
		inverseJoinColumns = @JoinColumn(name="user_id"))
	private List<User> users;

	@Basic
	@Column(name="research_project_offer_uuid")
	private String uuid;

	protected ResearchProjectOffer() {
		super();
	}
	
	public ResearchProjectOffer(int id) {
		super(id);
		setIsLoaded(false);
	}

	@JsonCreator
	public ResearchProjectOffer(@JsonProperty("id") Integer id, @JsonProperty("title") String title,
			@JsonProperty("creator") User creator, @JsonProperty("description") String description,
			@JsonProperty("descriptionLong") String descriptionLong, @JsonProperty("visible") boolean visible,
			@JsonProperty("users") List<User> users, @JsonProperty("threads") List<Thread> threads) {
		super(id, title, creator, description, descriptionLong, threads);

		this.visible = visible;
		this.users = users;
		setIsLoaded(true);
	}

	public Boolean getVisible() {
		return visible;
	}

	public void setVisible(Boolean visible) {
		this.visible = visible;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public boolean isUser(int userId) {
		boolean found = false;

		for (User user : users) {
			if (user.getId() == userId) {
				found = true;
				break;
			}
		}

		return found;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	/**
	 * if user is in list of users or creator return true.
	 *
	 * @param userId
	 * @return
	 */
	public boolean isUserInterestedInOffer(int userId) {
		for (User user : this.getUsers()) {
			if (user.getId() == userId) {
				return true;
			}
		}
		return false;
	}
}
