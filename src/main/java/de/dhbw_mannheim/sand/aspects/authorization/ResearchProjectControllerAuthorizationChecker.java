package de.dhbw_mannheim.sand.aspects.authorization;

import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import de.dhbw_mannheim.sand.model.LazyObject;
import de.dhbw_mannheim.sand.model.ResearchProject;
import de.dhbw_mannheim.sand.model.ResearchProjectOffer;
import de.dhbw_mannheim.sand.model.User;
import de.dhbw_mannheim.sand.repository.ResearchProjectOfferRepository;

public class ResearchProjectControllerAuthorizationChecker implements AuthorizationChecker {
	
	//Change as soon ResearchProjectRepository is available
	@Autowired
	ResearchProjectOfferRepository repository;
	
	@Override
	public boolean checkGetById(User user, int id) {
		// If user is involved, allow view
		// Warten auf Repository für ResearchProjectControllerAuthorizationChecker
		return repository.getOne(id).getUsers().contains(user);		
	}

	@Override
	public boolean checkAdd(User user, LazyObject object) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean checkUpdate(User user, LazyObject object) {
		ResearchProject rp = (ResearchProject)object;
		return true;
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
