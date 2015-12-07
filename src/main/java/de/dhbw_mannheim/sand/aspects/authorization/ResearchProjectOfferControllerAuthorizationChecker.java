package de.dhbw_mannheim.sand.aspects.authorization;

import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import de.dhbw_mannheim.sand.model.LazyObject;
import de.dhbw_mannheim.sand.model.ResearchProjectOffer;
import de.dhbw_mannheim.sand.model.User;
import de.dhbw_mannheim.sand.repository.ResearchProjectOfferRepository;

public class ResearchProjectOfferControllerAuthorizationChecker implements AuthorizationChecker{
	
	@Autowired
	private ResearchProjectOfferRepository repository;
	
	@Override
	public boolean checkGetById(User user, int id) {
		return true;
	}

	@Override
	public boolean checkAdd(User user, LazyObject object) {
		return(user.isAdmin() || user.isStudent() || user.isTeacher());
	}

	@Override
	public boolean checkUpdate(User user, LazyObject object) {
		//If user is Admin allow action
		if(user.isAdmin()) return true;
		
		ResearchProjectOffer pendingChange = (ResearchProjectOffer)object;
		ResearchProjectOffer existing = repository.getOne(pendingChange.getId());
		List<User> listBefore = existing.getUsers();
		List<User> listAfter  = pendingChange.getUsers();
		
		//If User is Creator, allow change
		if(existing.getCreator().equals(user)) return true;
		
		//Only Users who are Students or Teacher may add themselves as interested
		for(User u : listAfter){
			if(!listBefore.contains(u)){
				if(u.isTeacher() || u.isStudent()) return true;
			}
		}
		
		//Only Users who are Students or Teacher may remove themselves from interested
		for(User u : listBefore){
			if(!listAfter.contains(u)){
				if(u.isTeacher() || u.isStudent()) return true;
			}
		}	
		return false;
	}

	@Override
	public boolean checkDelete(User user, int id) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean checkOther(User user, String method, Object param) {
		// TODO Auto-generated method stub
		return false;
	}
	

}
