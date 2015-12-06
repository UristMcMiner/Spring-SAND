package de.dhbw_mannheim.sand.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import de.dhbw_mannheim.sand.model.Post;
import de.dhbw_mannheim.sand.model.Role;
import de.dhbw_mannheim.sand.model.Student;
import de.dhbw_mannheim.sand.model.User;

public interface PostRepository extends JpaRepository<Post,Integer> {

	//@Query("select r from Post r where r.user = ?1")
	//List<Role> findByUser(User user);
/*	@Query("select p from Post p where p.id = ?1")
	Post findById(int Id);
	
	@Query("select p from Post p where p.id = ?1")
	List<Post> findById(int Id);*/
	
	
}