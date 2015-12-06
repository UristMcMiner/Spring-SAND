package de.dhbw_mannheim.sand.service;

import java.util.List;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.sql.DataSource;

import org.apache.log4j.Logger;

import de.dhbw_mannheim.sand.model.Post;

@Stateless
@LocalBean
@Local(PostService.class)
public class PostServiceImpl implements PostService {
	
	private Logger logger = Logger.getLogger(PostServiceImpl.class);

	@Resource(name= "sand")
	private DataSource ds;
	
	public PostServiceImpl() {
		
	}
	
	@Override
	public Post getPostById(int id) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public List<Post> getAllPostsByThreadId(int id, boolean lazy) {
		// TODO Auto-generated method stub
		return null;
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
