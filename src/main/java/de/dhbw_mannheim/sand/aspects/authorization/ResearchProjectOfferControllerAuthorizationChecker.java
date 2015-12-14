package de.dhbw_mannheim.sand.aspects.authorization;

import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import de.dhbw_mannheim.sand.model.LazyObject;
import de.dhbw_mannheim.sand.model.ResearchProject;
import de.dhbw_mannheim.sand.model.ResearchProjectOffer;
import de.dhbw_mannheim.sand.model.User;
import de.dhbw_mannheim.sand.model.Thread;
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
	
	private boolean EqualsExceptUsers(ResearchProjectOffer rpo_b, ResearchProjectOffer rpo_a){
		return( rpo_b.getCreator().equals(rpo_a.getCreator()) &&
				rpo_b.getVisible() == rpo_a.getVisible() &&
				rpo_b.getDescription().equals(rpo_a.getDescription()) &&
				rpo_b.getDescriptionLong().equals(rpo_a.getDescriptionLong()) &&
				rpo_b.getId() == rpo_a.getId() &&
				ThreadsEqual(rpo_b.getThreads(),rpo_a.getThreads()) &&
				rpo_b.getTitle().equals(rpo_a.getTitle())
				);
	}
	private boolean ThreadsEqual(List<Thread> l_b, List<Thread> l_a){
			boolean test = true;
			for( Thread t : l_a){
				if(!l_b.contains(t)) test = false;
			}
			return test;
	}

}
