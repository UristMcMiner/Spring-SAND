package de.dhbw_mannheim.sand.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import de.dhbw_mannheim.sand.model.Post;
import de.dhbw_mannheim.sand.model.ResearchProjectOffer;
import de.dhbw_mannheim.sand.repository.PostRepository;
import de.dhbw_mannheim.sand.repository.ResearchProjectOfferRepository;

public class PostServiceImpl implements PostService {
	
	private Logger logger = Logger.getLogger(PostServiceImpl.class);
	
	@Autowired
	private PostRepository postRepository;
	
	@Autowired 
	private ResearchProjectOfferRepository rpoRepository;
	
	public PostServiceImpl() {
		
	}
	
	@Override
	public Post getPostById(int id) {
		Post post = postRepository.findByIdAndDeleted(id, 0);
		return post;
	}
	
	@Override
	public List<Post> getAllPostsByThreadId(int id, boolean lazy) {
		List<Post> allPostsByThreadId = new ArrayList<>();
		postRepository.findByThreadId(id);
		return allPostsByThreadId;
	}
	
	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public int addPost(Post post) {
		postRepository.save((Post)post);
		return post.getId();
	}
	
	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public void editPost(Post post) {
		postRepository.save((Post)post);		
	}
	
	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public void deletePostById(int id) {
		Post post = postRepository.findByIdAndDeleted(id, 0);
		if(post != null){
			postRepository.delete(post);
		}
				
	}

	@Override
	public int getResearchProjectByPostId(int id) {
		return postRepository.findByPostId(id);
	}

}
