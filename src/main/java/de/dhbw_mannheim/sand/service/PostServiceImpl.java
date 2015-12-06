package de.dhbw_mannheim.sand.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import de.dhbw_mannheim.sand.model.Post;
import de.dhbw_mannheim.sand.repository.PostRepository;

public class PostServiceImpl implements PostService {
	
	private Logger logger = Logger.getLogger(PostServiceImpl.class);

	@Autowired
	private PostRepository postRepository;
	
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
	public int addPost(Post post) {
		return 0;
	}
	
	@Override
	public void editPost(Post post) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void deletePostById(int id) {

		Post post = postRepository.findByIdAndDeleted(id, 0);
		postRepository.delete(post);		

	}

	@Override
	public int getResearchProjectByPostId(int id) {
		// TODO Auto-generated method stub
		return 0;
	}

}
