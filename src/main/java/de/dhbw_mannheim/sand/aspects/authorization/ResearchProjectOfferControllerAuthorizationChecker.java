package de.dhbw_mannheim.sand.aspects.authorization;

import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import de.dhbw_mannheim.sand.model.LazyObject;
import de.dhbw_mannheim.sand.model.ResearchProject;
import de.dhbw_mannheim.sand.model.ResearchProjectOffer;
import de.dhbw_mannheim.sand.model.User;
import de.dhbw_mannheim.sand.repository.ResearchProjectOfferRepository;
import de.dhbw_mannheim.sand.service.ResearchProjectOfferService;
@Component
public class ResearchProjectOfferControllerAuthorizationChecker implements AuthorizationChecker{
	
	@Autowired
	private ResearchProjectOfferRepository repository;
	@Autowired
	private ResearchProjectOfferService service;
	
	@Override
	public boolean checkGetById(User user, int id) {
		//Problem bei der Unterscheidung von Studienarbeit und Studienarbeit-Angebot
		ResearchProject rp = service.getProjectById(id);
		if(rp instanceof ResearchProjectOffer) return true;
		return false;
	}

	@Override
	public boolean checkAdd(User user, LazyObject object) {
		return(user.isStudent() || user.isTeacher());
	}

	@Override
	public boolean checkUpdate(User user, LazyObject object) {
		
		ResearchProjectOffer pendingChange = (ResearchProjectOffer)object;
		//Changed to Service
		ResearchProjectOffer existing = service.getProjectById(pendingChange.getId());
		List<User> listBefore = existing.getUsers();
		List<User> listAfter  = pendingChange.getUsers();
		if(user.isStudent() || user.isTeacher()){
			//If User is Creator, allow change (May add or delete Interested user aswell, is allowed by the task)
			if(existing.getCreator().equals(user)) return true;
		

			//Only Users who are Students or Teacher may add themselves as interested
			for(User u : listAfter){
				if(!listBefore.contains(u)){
					if(u.equals(user) && EqualsExceptUsers(existing,pendingChange)) return true;
				}
			}
		
			//Only Users who are Students or Teacher may remove themselves from interested
			for(User u : listBefore){
				if(!listAfter.contains(u)){
					if(u.equals(user) && EqualsExceptUsers(existing,pendingChange)) return true;
				}
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
	
	private boolean EqualsExceptUsers(ResearchProjectOffer rpo1, ResearchProjectOffer rpo2){
		return( rpo1.getCreator().equals(rpo2.getCreator()) &&
				rpo1.getVisible() == rpo2.getVisible() &&
				rpo1.getDescription().equals(rpo2.getDescription()) &&
				rpo1.getDescriptionLong().equals(rpo2.getDescriptionLong()) &&
				rpo1.getId() == rpo2.getId() &&
				rpo1.getThreads().equals(rpo2.getThreads()) &&
				rpo1.getTitle().equals(rpo2.getTitle()) &&
				rpo1.getUuid() == rpo2.getUuid()
				);
	}

}
