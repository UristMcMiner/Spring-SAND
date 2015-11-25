package de.dhbw_mannheim.sand.aspects;

import de.dhbw_mannheim.sand.model.LazyObject;
import de.dhbw_mannheim.sand.model.User;

public interface AuthorizationChecker {

	public abstract boolean checkGetById(User user, int id);

	public abstract boolean checkAdd(User user, LazyObject object);

	public abstract boolean checkUpdate(User user, LazyObject object);

	public abstract boolean checkDelete(User user, int id);

}