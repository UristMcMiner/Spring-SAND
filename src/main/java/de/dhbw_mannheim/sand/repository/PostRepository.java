package de.dhbw_mannheim.sand.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import de.dhbw_mannheim.sand.model.Post;

public interface PostRepository extends JpaRepository<Post,Integer> {

	Post findByIdAndDeleted(int id, int deleted);
	
	@Query("select p from Post p where p.thread_id = ?")
	List<Post> findByThreadId(int threadId);
}