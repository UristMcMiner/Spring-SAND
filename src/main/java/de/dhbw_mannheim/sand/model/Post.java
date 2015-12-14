package de.dhbw_mannheim.sand.model;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name="post")
@JsonAutoDetect
@JsonInclude(value = Include.NON_NULL)
public class Post extends LazyObject {

	@Temporal(TemporalType.TIMESTAMP)
	private Date timestamp;

	private String text;

	@ManyToOne
	@JoinColumn(name="creator_user_id")
	private User creator;

	@ManyToOne
	@JoinColumn(name="thread_id")
	private Thread thread;

	@OneToMany(fetch=FetchType.LAZY, mappedBy="post")
	private List<PostAttachment> attachments;

	private Boolean hidden;

	protected Post() {
		super();
	}
	
	public Post(int id) {
		super(id);
		setIsLoaded(false);
	}

	@JsonCreator
	public Post(@JsonProperty("id") Integer id, @JsonProperty("timestamp") Timestamp timestamp,
			@JsonProperty("text") String text, @JsonProperty("creator") User creator,
			@JsonProperty("thread") Thread thread, @JsonProperty("attachments") List<PostAttachment> attachments,
			@JsonProperty("hidden") Boolean hidden) {
		super(id);
		this.setTimestamp(timestamp);
		this.setText(text);
		this.setCreator(creator);
		this.setThread(thread);
		this.setPostAttachments(attachments);
		this.setHidden(hidden);
		setIsLoaded(true);
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public User getCreator() {
		return creator;
	}

	public void setCreator(User creator) {
		this.creator = creator;
	}

	public Thread getThread() {
		return thread;
	}

	public void setThread(Thread thread) {
		this.thread = thread;
	}

	public List<PostAttachment> getAttachments() {
		return attachments;
	}

	public void setPostAttachments(List<PostAttachment> attachments) {
		this.attachments = attachments;
	}

	public Boolean getHidden() {
		return hidden;
	}

	public void setHidden(Boolean hidden) {
		this.hidden = hidden;
	}
}
