package de.dhbw_mannheim.sand.service;

import java.util.List;

import de.dhbw_mannheim.sand.model.Post;

public interface PostService {
	/**
	 *
	 * @param id
	 *            of a Post as integer
	 * @return Post
	 */
	public Post getPostById(int id);
	
	/**
	 *
	 * @param id
	 *            of a Thread as integer
	 * @param lazy
	 *            Whether the returned posts should be loaded lazy or not
	 * @return List<Post>
	 */
	public List<Post> getAllPostsByThreadId(int id, boolean lazy);
	
	/**
	 *
	 * @param post
	 * @return Post id as integer
	 */
	public int addPost(Post post);
	
	/**
	 *
	 * @param post
	 *            (will be changed based on the id)
	 */
	public void editPost(Post post);
	
	/**
	 *
	 * @param id
	 *            of a Post as integer
	 */
	public void deletePostById(int id);

	/**
	 * 
	 * @param id
	 * @return id of ResearchProject as integer
	 */
	public int getResearchProjectByPostId(int id);


}
