package de.dhbw_mannheim.sand.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import de.dhbw_mannheim.sand.model.Thread;
import de.dhbw_mannheim.sand.repository.ThreadRepository;

public class ThreadServiceImpl implements ThreadService {

	@Autowired
	ThreadRepository thread_rep;
	
	@Override
	public List<Thread> getAllThreadsByResearchProjectId(int id,
			boolean lazy) {		
		thread_rep.findByResearchProject(id);
		return null;
	}

	@Override
	public Thread getThreadById(int id) {
		thread_rep.findOne(id);
		return null;
	}

	@Override
	public int addThread(Thread thread) {
		return (int) thread_rep.save(thread).getId();
		
	}

	@Override
	public void editThread(Thread thread) {
		thread_rep.save(thread).getId();
		
	}

	@Override
	public void deleteThreadById(int id) {
		Thread t = thread_rep.findOne(id);
//		if (t != null) {
//			Calendar cal = Calendar.getInstance();
//			cal.add(Calendar.DATE, -1);
//			Date yesterday = new java.sql.Date(cal.getTimeInMillis());
//			t.setEndDate(yesterday);
//	}
		thread_rep.delete(t);
	}
}
