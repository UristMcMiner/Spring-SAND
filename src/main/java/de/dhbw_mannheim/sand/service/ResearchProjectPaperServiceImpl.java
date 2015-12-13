package de.dhbw_mannheim.sand.service;

//import java.util.ArrayList;
//import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import de.dhbw_mannheim.sand.model.ResearchProjectPaper;
import de.dhbw_mannheim.sand.repository.ResearchProjectOfferRepository;
import de.dhbw_mannheim.sand.repository.ResearchProjectPaperRepository;

import org.springframework.stereotype.Service;

@Service
public class ResearchProjectPaperServiceImpl implements
		ResearchProjectPaperService {

	@Autowired
	ResearchProjectPaperRepository paper_rep;
	
	@Autowired
	ResearchProjectOfferRepository offer_rep;
	
	@Override
	public void editProject(ResearchProjectPaper paper) {
		
		if (offer_rep.findOne(paper.getId()) != null)			
			offer_rep.delete(paper.getId());
		else if (paper_rep.findOne(paper.getId()) == null)
			throw new IllegalArgumentException("Neither offer nor paper exists to given ID.");
		
		paper_rep.save(paper);	
	}

	//not used
//	@Override
//	public List<ResearchProjectPaper> getAllProjects() {
//		ArrayList<ResearchProjectPaper> papers = new ArrayList<ResearchProjectPaper>();
//		papers.addAll(paper_rep.findAll());
//		return papers;
//	}
//
//	@Override
//	public ResearchProjectPaper getProjectByID(int id) {
//		return paper_rep.findOne(id);
//	}
//
//	@Override
//	public int addProjectPaper(ResearchProjectPaper rp) {
//		return paper_rep.save(rp).getId();
//	}
//
//	@Override
//	public ResearchProjectPaper editProjectPaper(ResearchProjectPaper rp) {
//		return paper_rep.save(rp);
//	}
//
//	@Override
//	public void deleteProjectPaper(ResearchProjectPaper rp) {
//		rp.delete();
//		paper_rep.save(rp);
//	}

	}

