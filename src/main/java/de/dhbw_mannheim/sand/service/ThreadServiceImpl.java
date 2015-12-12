package de.dhbw_mannheim.sand.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.dhbw_mannheim.sand.model.Thread;
import de.dhbw_mannheim.sand.repository.ThreadRepository;

@Service
public class ThreadServiceImpl implements ThreadService {

	@Autowired
	ThreadRepository thread_rep;
	
	@Override
	public List<Thread> getAllThreadsByResearchProjectId(int id) {		
		thread_rep.findByResearchProject(id);
		return null;
	}

	
	//Manchmal wird hier ein Fehler angezeigt, der keiner ist.
	//"Cannot cast from List<Thread> to Thread." Alle Input-/Return-Paramater sind Threads,
	//es liegt also kein Fehler vor. Diese Klasse speichern löst das Problem normal.
	@Override
	public Thread getThreadById(int id) {
		return thread_rep.findOne(id);
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
		if (t == null)
			throw new IllegalArgumentException("Thread does not exist.");
		else {
			t.delete();
			thread_rep.save(t);
		}
	}
}
