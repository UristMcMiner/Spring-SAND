package de.dhbw_mannheim.sand.model;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;


@Entity
@Table(name="attachment")
@JsonAutoDetect
@JsonInclude(value = Include.NON_NULL)
public class PostAttachment extends LazyObject {

	@ManyToOne
	@JoinColumn(name="post_id")
	private Post post;

	@Transient
	private Event event;

	private String name;

	@Lob
	private byte[] content;
	
	@JsonIgnore
	int deleted;
	
	protected PostAttachment() {
		super();
	}
	
	public PostAttachment(int id) {
		super(id);
		setIsLoaded(false);
	}

	@JsonCreator
	public PostAttachment(@JsonProperty("id") Integer id, @JsonProperty("post") Post post,
			@JsonProperty("event") Event event,
			@JsonProperty("name") String name) {
		super(id);
		this.setPost(post);
		this.setEvent(event);
		this.setName(name);
		setIsLoaded(true);
	}

	public Post getPost() {
		return post;
	}

	public void setPost(Post post) {
		this.post = post;
	}

	public Event getEvent() {
		return event;
	}

	public void setEvent(Event event) {
		this.event = event;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public byte[] getContent() {
		return content;
	}

	public void setContent(byte[] content) {
		this.content = content;
	}

	public int getDeleted() {
		return deleted;
	}

	public void setDeleted(int deleted) {
		this.deleted = deleted;
	}
}
