package de.dhbw_mannheim.sand.service;

import java.util.List;

//import de.dhbw_mannheim.sand.model.Thread;

public interface ThreadService {

	/**
	 *
	 * @return
	 */
	public List<Thread> getAllThreadsByResearchProjectId(int id, boolean lazy);

	/**
	 *
	 * @param id
	 *            of a Thread as integer
	 * @return Thread
	 */
	public Thread getThreadById(int id);

	/**
	 *
	 * @param thread
	 * @return Thread id as integer
	 */
	public int addThread(Thread thread);

	/**
	 *
	 * @param thread
	 *            (will be changed based on the id)
	 */
	public void editThread(Thread thread);

	/**
	 *
	 * @param id
	 *            of a Thread as integer
	 */
	public void deleteThreadById(int id);

}
