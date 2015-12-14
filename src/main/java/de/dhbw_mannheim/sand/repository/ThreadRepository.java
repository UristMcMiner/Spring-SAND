package de.dhbw_mannheim.sand.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import de.dhbw_mannheim.sand.model.LazyObject;
import de.dhbw_mannheim.sand.model.ResearchProject;
import de.dhbw_mannheim.sand.model.Role;
import de.dhbw_mannheim.sand.model.Thread;
import de.dhbw_mannheim.sand.model.User;

public interface ThreadRepository extends JpaRepository<Thread,Integer> {
	
	@Query("select t from Thread t where t.researchProject.id = ?1 and t.hidden=0")
	List<Thread> findByResearchProject(int rp);
	
	@Query("select t from Thread t where t.id = ?1 and t.hidden=0")
	Thread findOne(int id);
	
	@Query("select t from Thread t where t.hidden=0")
	List<Thread> findAll();
	
	
}