package de.dhbw_mannheim.sand.service;

import java.util.List;

import de.dhbw_mannheim.sand.model.*;

public interface ResearchProjectOfferService {

	public ResearchProjectOffer getProjectByUuid(int uuid);
	
	/**
	 *
	 * @return List of all ResearchProjects
	 */
	public List<ResearchProjectOffer> getAllProjects();

	/**
	 *
	 * @param id
	 *            of a User as integer
	 * @return List of all ResearchProjects of a User
	 */
	public List<ResearchProjectOffer> getProjectsByUserId(int id);

	/**
	 *
	 * @param id
	 *            of a ResearchProject as integer
	 * @return ResearchProject
	 */
	public ResearchProjectOffer getProjectById(int id);

	/**
	 *
	 * @param project
	 * @return ResearchProject id as integer
	 */
	public int addProject(ResearchProjectOffer project);

	/**
	 *
	 * @param project
	 *            (will be changed based on the id)
	 */
	public void editProject(ResearchProjectOffer project);

	/**
	 *
	 * @param id
	 *            of a ResearchProject as integer
	 */
	public void deleteProjectById(int id);
	
}
