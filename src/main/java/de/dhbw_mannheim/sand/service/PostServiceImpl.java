package de.dhbw_mannheim.sand.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import de.dhbw_mannheim.sand.model.Post;
import de.dhbw_mannheim.sand.repository.PostRepository;
import de.dhbw_mannheim.sand.repository.ResearchProjectOfferRepository;
@Service
public class PostServiceImpl implements PostService {
	
	//private Logger logger = Logger.getLogger(PostServiceImpl.class);
	
	@Autowired
	private PostRepository postRepository;
	
	@Autowired 
	private ResearchProjectOfferRepository rpoRepository;
	
	public PostServiceImpl() {
		
	}
	
	@Override
	public Post getPostById(int id) {
		Post post = postRepository.findOne(id);
		return post;
	}
	
	@Override
	public List<Post> getAllPostsByThreadId(int id, boolean lazy) {
		List<Post> allPostsByThreadId = new ArrayList<>();
		for(Post post : postRepository.findByThreadId(id)){
			allPostsByThreadId.add(post);
		}
		return allPostsByThreadId;
	}
	
	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public int addPost(Post post) {
		return postRepository.save(post).getId();
	}
	
	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public void editPost(Post post) {
		postRepository.saveAndFlush(post);		
	}
	
	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public void deletePostById(int id) {
		Post post = postRepository.findOne(id);
		if(post != null){
			postRepository.delete(post);
		}				
	}

	@Override
	public Integer getResearchProjectByPostId(int id) {
		return postRepository.findByPostId(id);
	}

}
