package de.dhbw_mannheim.sand.service;

import java.io.InputStream;
import java.util.List;

import org.apache.commons.lang.NotImplementedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.dhbw_mannheim.sand.model.PostAttachment;
import de.dhbw_mannheim.sand.repository.PostAttachmentRepository;
import de.dhbw_mannheim.sand.repository.ResearchProjectOfferRepository;

@Service
public class PostAttachmentServiceImpl implements PostAttachmentService {

	@Autowired
	private PostAttachmentRepository repository;
	
	@Override
	public PostAttachment getPostAttachmentById(int id) {
		
		PostAttachment pA = repository.findByIdAndDeleted(id,0);
		if(pA == null)
		{
			throw new IllegalArgumentException("id not found!");
		}
		return pA;
	}

	@Override
	public List<PostAttachment> getAllPostAttachmentsByPostId(int id,
			boolean lazy) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int addPostAttachment(PostAttachment postAttachment) {
		
		return repository.save(postAttachment).getId();
		
	}

	@Override
	public void editPostAttachment(PostAttachment postAttachment) {
		
		throw new NotImplementedException();
		
	}

	@Override
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
	public void deleteAttachmentsByPostId(int id) {
		
		
		
	}

	@Override
	public void setContentById(int id, byte[] bytes) {
		// TODO Auto-generated method stub

	}

	@Override
	public InputStream getContentById(int id) {
		// TODO Auto-generated method stub
		return null;
	}

}
