package de.dhbw_mannheim.sand.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import de.dhbw_mannheim.sand.model.Post;


public interface PostRepository extends JpaRepository<Post,Integer> {
	
	//@Query("select p from Post p where p.id = ?1")
	//List<Post> findById(int Id);*/
	
}