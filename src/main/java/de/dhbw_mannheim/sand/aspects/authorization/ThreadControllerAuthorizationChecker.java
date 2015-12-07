package de.dhbw_mannheim.sand.aspects.authorization;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import de.dhbw_mannheim.sand.model.Thread;
import de.dhbw_mannheim.sand.model.LazyObject;
import de.dhbw_mannheim.sand.model.ResearchProjectOffer;
import de.dhbw_mannheim.sand.model.User;
import de.dhbw_mannheim.sand.repository.ResearchProjectOfferRepository;

public class ThreadControllerAuthorizationChecker implements AuthorizationChecker {
	
	//Change as soon ThreadRepository is available
	//@Autowired
	//ThreadRepository repository;
	
	@Autowired
	ResearchProjectOfferRepository rpo_repository;
	
	@Override
	public boolean checkGetById(User user, int id) {
		//Searching all Threads from ResearchProjectOffers, no method for getting a RPO from a Thread
		List<ResearchProjectOffer> rpol = rpo_repository.findAll();
		for ( ResearchProjectOffer rpo : rpol ){
			List<Thread> tl = rpo.getThreads();
			for ( Thread t : tl ){
				if(t.getId() == id){
					return ( rpo.getUsers().contains(user) || rpo.getCreator().equals(user));
				}
			}
		}
		return false;
	}

	@Override
	public boolean checkAdd(User user, LazyObject object) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean checkUpdate(User user, LazyObject object) {
		return true;
	}

	@Override
	public boolean checkDelete(User user, int id) {
		Thread t = new Thread(id);
		return t.getResearchProject().getCreator().equals(user);
	}

	@Override
	public boolean checkOther(User user, String method, Object param) {
		// TODO Auto-generated method stub
		return false;
	}
}
