//package de.dhbw_mannheim.sand.service;
//
//import java.util.List;
//
//import javax.ejb.Stateless;
//import javax.enterprise.inject.Alternative;
//import javax.persistence.EntityManager;
//import javax.persistence.PersistenceContext;
//import javax.persistence.TypedQuery;
//
//import de.dhbw_mannheim.sand.model.Thread;
//import de.dhbw_mannheim.sand.persistence.IThreadPersistence;
//
//@Stateless
//@Alternative
//public class ThreadServiceImpl implements IThreadPersistence {
//
//	@PersistenceContext(unitName="sand")
//	private EntityManager entityManager;
//	
//	@Override
//	public List<Thread> getAllThreadsByResearchProjectId(int id, boolean lazy) {
//		 String jpql = "select t from Thread t where t.hidden = 0 and t.researchProject.id = "+id;
//		 TypedQuery<Thread> query = entityManager.createQuery(jpql, Thread.class);
//		 List<Thread> threads = query.getResultList();
//		 if (!lazy) {
//			 for (Thread thread: threads) {
//				 thread.toString();
//				 thread.getResearchProject();
//				 thread.getPosts();
//			 }
//		 }
//		 return threads;
//	}
//
//	@Override
//	public Thread getThreadById(int id) {
//		Thread thread = entityManager.find(Thread.class, id);
//		if (thread != null) {
//			 thread.toString();
//			 thread.getResearchProject();
//			 thread.getPosts();
//		}
//		return thread;
//	}
//
//	@Override
//	public int addThread(Thread thread) {
//		entityManager.persist(thread);
//		entityManager.flush();
//		return thread.getId();
//	}
//
//	@Override
//	public void editThread(Thread thread) {
//		entityManager.merge(thread);
//		entityManager.flush();
//	}
//
//	@Override
//	public void deleteThreadById(int id) {
//		Thread thread = entityManager.find(Thread.class, id);
//		if (thread != null) {
//			thread.setHidden(1);
//			entityManager.flush();
//		} else {
//			throw new RuntimeException();
//		}
//	}
//
//}
