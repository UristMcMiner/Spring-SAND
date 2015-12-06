package de.dhbw_mannheim.sand.aspects.authorization;

import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import de.dhbw_mannheim.sand.model.LazyObject;
import de.dhbw_mannheim.sand.model.ResearchProjectOffer;
import de.dhbw_mannheim.sand.model.User;

public class ResearchProjectControllerAuthorizationChecker implements AuthorizationChecker {

	@Override
	public boolean checkGetById(User user, int id) {
		// If user is involved, allow view
		// Warten auf Repository für ResearchProjectControllerAuthorizationChecker
		if(repository.getOne(id).getUsers().contains(user));
		return true;
	}

	@Override
	public boolean checkAdd(User user, LazyObject object) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean checkUpdate(User user, LazyObject object) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean checkDelete(User user, int id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean checkOther(User user, String method, Object param) {
		// TODO Auto-generated method stub
		return false;
	}
}
