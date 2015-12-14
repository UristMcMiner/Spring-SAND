package de.dhbw_mannheim.sand.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import de.dhbw_mannheim.sand.model.ResearchProjectOffer;
import de.dhbw_mannheim.sand.model.User;
import de.dhbw_mannheim.sand.repository.ResearchProjectOfferRepository;
import de.dhbw_mannheim.sand.repository.UserRepository;



import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ResearchProjectOfferServiceImpl implements
		ResearchProjectOfferService {
	
	@Autowired
	private ResearchProjectOfferRepository repository;
	
	@Autowired
	private UserRepository userRepository;

	@Override
	public ResearchProjectOffer getProjectByUuid(String uuid) {
		ResearchProjectOffer res = repository.findByUuidAndDeleted(uuid,0);
		if(res !=null)
		{
			return res;
		}
		else
			return null;
	}

	@Override
	public List<ResearchProjectOffer> getAllProjects() {
		return repository.findByDeleted(0);
	}

	@Override
	public List<ResearchProjectOffer> getProjectsByUserId(int id) {
		User user = userRepository.findById(id);
		return repository.findByInterestedUser(user);
	}

	@Override
	public ResearchProjectOffer getProjectById(int id) {
		ResearchProjectOffer res = repository.findByIdAndDeleted(id,0);
		return res;
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public int addProject(ResearchProjectOffer project) {
		return repository.save(project).getId();
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public void editProject(ResearchProjectOffer project) {
		repository.saveAndFlush(project);
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public void deleteProjectById(int id) {
		ResearchProjectOffer offer = repository.findByIdAndDeleted(id,0);
		if(offer ==null) throw new IllegalArgumentException("id not found");
		offer.setDeleted(1);
		repository.saveAndFlush(offer);
	}

}
