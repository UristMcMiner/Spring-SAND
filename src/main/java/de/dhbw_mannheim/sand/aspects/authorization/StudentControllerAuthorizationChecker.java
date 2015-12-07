package de.dhbw_mannheim.sand.aspects.authorization;

import de.dhbw_mannheim.sand.model.LazyObject;
import de.dhbw_mannheim.sand.model.User;

public class StudentControllerAuthorizationChecker implements AuthorizationChecker{

	@Override
	public boolean checkGetById(User user, int id) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean checkAdd(User user, LazyObject object) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean checkUpdate(User user, LazyObject object) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean checkDelete(User user, int id) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean checkOther(User user) {
		// TODO Auto-generated method stub
		return false;
	}

}
