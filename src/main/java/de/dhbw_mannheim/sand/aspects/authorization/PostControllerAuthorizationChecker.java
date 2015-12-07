package de.dhbw_mannheim.sand.aspects.authorization;

import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import de.dhbw_mannheim.sand.model.LazyObject;
import de.dhbw_mannheim.sand.model.Post;
import de.dhbw_mannheim.sand.model.ResearchProject;
import de.dhbw_mannheim.sand.model.ResearchProjectOffer;
import de.dhbw_mannheim.sand.model.Thread;
import de.dhbw_mannheim.sand.model.User;
import de.dhbw_mannheim.sand.repository.ResearchProjectOfferRepository;

public class PostControllerAuthorizationChecker implements AuthorizationChecker {
	
	//Change as soon PostRepository is available
	//@Autowired
	//PostRepository repository;
	
	@Autowired
	ResearchProjectOfferRepository rpo_repository;
	
	
	@Override
	public boolean checkGetById(User user, int id) {
		return true;	
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
		Post post  = new Post(id);
		Thread thread = post.getThread();
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
	public boolean checkOther(User user, String method, Object param) {
		// TODO Auto-generated method stub
		return false;
	}
}
