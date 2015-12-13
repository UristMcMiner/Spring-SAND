package de.dhbw_mannheim.sand.aspects.authorization;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import de.dhbw_mannheim.sand.model.ResearchProject;
import de.dhbw_mannheim.sand.model.Thread;
import de.dhbw_mannheim.sand.model.LazyObject;
import de.dhbw_mannheim.sand.model.ResearchProjectOffer;
import de.dhbw_mannheim.sand.model.User;
import de.dhbw_mannheim.sand.repository.ResearchProjectOfferRepository;
import de.dhbw_mannheim.sand.service.ThreadService;


public class ThreadControllerAuthorizationChecker implements AuthorizationChecker {
	
	
	@Autowired
	ThreadService service;
	
	@Override
	public boolean checkGetById(User user, int id) {
		Thread thread = service.getThreadById(id);
		ResearchProject rp = thread.getResearchProject();
		if( rp instanceof ResearchProjectOffer){
			ResearchProjectOffer rpo = (ResearchProjectOffer)rp;
			return (rpo.getUsers().contains(user) || rpo.getCreator().equals(user));
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
		Thread t = service.getThreadById(id);
		return t.getResearchProject().getCreator().equals(user);
	}

	@Override
	public boolean checkOther(User user, String method, Object param) {
		// TODO Auto-generated method stub
		return false;
	}
}
