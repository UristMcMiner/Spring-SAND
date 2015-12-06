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
import de.dhbw_mannheim.sand.repository.PostRepository;

public class PostServiceImpl implements PostService {
	
	private Logger logger = Logger.getLogger(PostServiceImpl.class);
	
	@Autowired
	private PostRepository postRepository;
	
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
		return allPostsByThreadId;
	}
	
	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public int addPost(Post post) {
		postRepository.save((Post)post);
		return post.getId();
	}
	
	@Override
	public void editPost(Post post) {
		postRepository.save((Post)post);		
	}
	
	@Override
	public void deletePostById(int id) {
		// TODO Auto-generated method stub

	}

	@Override
	public int getResearchProjectByPostId(int id) {
		// TODO Auto-generated method stub
		return 0;
	}

}
