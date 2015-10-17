package de.dhbw_mannheim.sand.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import de.dhbw_mannheim.sand.model.*;

public interface ResearchProjectOfferRepository extends JpaRepository<ResearchProjectOffer,Integer> {
	
	@Query("select o from ResearchProjectOffer o where o.creator = ?1 and o.deleted=0")
	List<ResearchProjectOffer> findByCreator(User user);
	
	@Query("select o from ResearchProjectOffer o inner join o.users u where u = ?1 and o.deleted=0")
	List<ResearchProjectOffer> findByInterestedUser(User user);

}