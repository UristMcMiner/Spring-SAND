package de.dhbw_mannheim.sand.service;

import java.io.InputStream;
import java.util.List;

import de.dhbw_mannheim.sand.model.PostAttachment;

public interface PostAttachmentService {

	public PostAttachment getPostAttachmentById(int id);

	public List<PostAttachment> getAllPostAttachmentsByPostId(int id, boolean lazy);

	public int addPostAttachment(PostAttachment postAttachment);

	public void editPostAttachment(PostAttachment postAttachment);

	public void deleteAttachmentById(int id);

	public void deleteAttachmentsByPostId(int id);

	public void setContentById(int id, byte[] bytes);

	public InputStream getContentById(int id);
	
}
