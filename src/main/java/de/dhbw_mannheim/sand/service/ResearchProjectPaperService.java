package de.dhbw_mannheim.sand.service;

import java.util.List;

import de.dhbw_mannheim.sand.model.*;

public interface ResearchProjectPaperService {
	public void editProject(ResearchProjectPaper paper);
	
	public List<ResearchProjectPaper> getAllProjects();
	
	public ResearchProjectPaper getProjectByID(int id);
	
	public int addProjectPaper(ResearchProjectPaper rp);
	
	public ResearchProjectPaper editProjectPaper(ResearchProjectPaper rp);
	
	public void deleteProjectPaper(ResearchProjectPaper paper);

}
