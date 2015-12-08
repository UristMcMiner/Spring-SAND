package de.dhbw_mannheim.sand.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import de.dhbw_mannheim.sand.model.Post;

public interface PostRepository extends JpaRepository<Post,Integer> {

		
	@Query("select p from Post p where p.thread.id = ?")
	List<Post> findByThreadId(int threadId);
	
	@Query("SELECT rp.id FROM ResearchProject rp"
			+ " INNER JOIN rp.threads t INNER JOIN t.posts p"
			+ " WHERE p.id = ? AND p.hidden = 0 AND t.hidden = 0")
	int findByPostId(int postId);
}