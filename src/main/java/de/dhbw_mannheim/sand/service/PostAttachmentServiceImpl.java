package de.dhbw_mannheim.sand.service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.commons.lang.NotImplementedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import de.dhbw_mannheim.sand.model.Post;
import de.dhbw_mannheim.sand.model.PostAttachment;
import de.dhbw_mannheim.sand.repository.PostAttachmentRepository;
import de.dhbw_mannheim.sand.repository.PostRepository;
import de.dhbw_mannheim.sand.repository.ResearchProjectOfferRepository;

@Service
public class PostAttachmentServiceImpl implements PostAttachmentService {

	@Autowired
	private PostAttachmentRepository repository;
	
	
	@Override
	public PostAttachment getPostAttachmentById(int id) {
		
		PostAttachment pA = repository.findByIdAndDeleted(id,0);
		return pA;
	}

	@Override
	public List<PostAttachment> getAllPostAttachmentsByPostId(int id,
			boolean lazy) {
		//bei lazy neue postattachments nur mit id
		return repository.findByPostId(id);
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public int addPostAttachment(PostAttachment postAttachment) {
		
		return repository.save(postAttachment).getId();
		
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public void editPostAttachment(PostAttachment postAttachment) {
		
		throw new NotImplementedException();
		
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public void deleteAttachmentById(int id) {
		
		PostAttachment pA = repository.findByIdAndDeleted(id,0);
		if(pA == null)
		{
			throw new IllegalArgumentException("id not found!");
		}
		pA.setDeleted(1);
		repository.saveAndFlush(pA);
		
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public void deleteAttachmentsByPostId(int id) {
		
		List<PostAttachment> pAs = repository.findAll();
		for(PostAttachment pA : pAs)
		{
			if(pA.getPost().getId() == id)
			{
				deleteAttachmentById(pA.getId());
			}
		}
		
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public void setContentById(int id, byte[] bytes) {
		
		PostAttachment pA = repository.findByIdAndDeleted(id, 0);
		if(pA==null)
			throw new IllegalArgumentException("id not found");
		pA.setContent(bytes);
		repository.saveAndFlush(pA);
		
	}

	@Override
	public InputStream getContentById(int id) {

		PostAttachment pA = repository.findByIdAndDeleted(id, 0);
		if(pA==null)
			throw new IllegalArgumentException("id not found");
		InputStream is = new ByteArrayInputStream(pA.getContent());
		return is;
		
	}

}
