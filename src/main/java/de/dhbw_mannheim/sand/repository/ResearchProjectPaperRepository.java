package de.dhbw_mannheim.sand.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import de.dhbw_mannheim.sand.model.*;

public interface ResearchProjectPaperRepository extends JpaRepository<ResearchProjectPaper,Integer> {

	@Query("select r from ResearchProjectPaper r where r.id=?1 and r.deleted=0")
	public ResearchProjectPaper findOne(int id); 
	
	@Query("select r from ResearchProjectPaper r where r.deleted=0")
	public List<ResearchProjectPaper> findAll();
}