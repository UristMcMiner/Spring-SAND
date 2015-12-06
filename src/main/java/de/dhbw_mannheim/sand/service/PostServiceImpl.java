package de.dhbw_mannheim.sand.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import de.dhbw_mannheim.sand.model.Post;
import de.dhbw_mannheim.sand.repository.PostRepository;

@Stateless
@LocalBean
@Local(PostService.class)
public class PostServiceImpl implements PostService {
	
	private Logger logger = Logger.getLogger(PostServiceImpl.class);

	@Resource(name= "sand")
	private DataSource ds;
	
	public PostServiceImpl() {
		
	}
	@Autowired
	private PostRepository postRepository;
	
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
	public int addPost(Post post) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
	public void editPost(Post post) {
		// TODO Auto-generated method stub
		
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
