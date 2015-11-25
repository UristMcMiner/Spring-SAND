package de.dhbw_mannheim.sand.aspects;

import org.springframework.stereotype.Component;

import de.dhbw_mannheim.sand.model.LazyObject;
import de.dhbw_mannheim.sand.model.User;

@Component
public class UserControllerAuthenticationChecker implements AuthorizationChecker {
	
	/* (non-Javadoc)
	 * @see de.dhbw_mannheim.sand.aspects.AuthorizationChecker#checkGetById(de.dhbw_mannheim.sand.model.User, int)
	 */
	@Override
	public boolean checkGetById(User user, int id) {
		return true;
	}

	/* (non-Javadoc)
	 * @see de.dhbw_mannheim.sand.aspects.AuthorizationChecker#checkAdd(de.dhbw_mannheim.sand.model.User, de.dhbw_mannheim.sand.model.LazyObject)
	 */
	@Override
	public boolean checkAdd(User user, LazyObject object) {
		return true;
	}

	/* (non-Javadoc)
	 * @see de.dhbw_mannheim.sand.aspects.AuthorizationChecker#checkUpdate(de.dhbw_mannheim.sand.model.User, de.dhbw_mannheim.sand.model.LazyObject)
	 */
	@Override
	public boolean checkUpdate(User user, LazyObject object) {
		return true;
	}

	/* (non-Javadoc)
	 * @see de.dhbw_mannheim.sand.aspects.AuthorizationChecker#checkDelete(de.dhbw_mannheim.sand.model.User, int)
	 */
	@Override
	public boolean checkDelete(User user, int id) {
		return true;
	}

}
