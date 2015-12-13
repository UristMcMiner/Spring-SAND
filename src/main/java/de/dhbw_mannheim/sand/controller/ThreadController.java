package de.dhbw_mannheim.sand.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import de.dhbw_mannheim.sand.repository.ThreadRepository;
import de.dhbw_mannheim.sand.service.ThreadService;
import de.dhbw_mannheim.sand.model.Post;
import de.dhbw_mannheim.sand.model.ResearchProject;
import de.dhbw_mannheim.sand.model.Thread;



@CrossOrigin
@RestController
@RequestMapping(value = "/sand/threads")
public class ThreadController {
	
        @Autowired
        private ThreadRepository repository;
	
	@Autowired
	private ThreadService service;
	
	
	// @LoggedIn
	@RequestMapping(method = RequestMethod.GET, value = "/{id}")
	@ResponseBody
	public ResponseEntity<Thread> getById(
	@RequestHeader(value="authorization", defaultValue="X") String authorization, @PathVariable(value = "id") int id) {
            try {
                Thread thread = service.getThreadById(id);
                if (thread != null) {
                	modifyThread(thread);
                    return new ResponseEntity<Thread>(thread, HttpStatus.OK);
                }else{	
                    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                }
            } catch (Exception e) {
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
	}
	
	 
	@RequestMapping(method = RequestMethod.POST, value="/")
	public ResponseEntity<Thread> add(
	@RequestHeader(value="authorization", defaultValue="X") String authorization, @RequestBody Thread thread) {
           try{            
                int id = service.addThread(thread);
                Thread added = service.getThreadById(id);
                modifyThread(added);
                return new ResponseEntity<Thread>(added , HttpStatus.CREATED);
           }catch(Exception e){
               return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
           }
	}
	
	
	@RequestMapping(method = RequestMethod.PUT, value = "/")
	public ResponseEntity<Thread> edit(
	@RequestHeader(value="authorization", defaultValue="X") String authorization, @RequestBody Thread thread) {
	
            // Thread not allowed to edit
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
	
	
	
	@RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
	public ResponseEntity<Thread> delete(
	@RequestHeader(value="authorization", defaultValue="X") String authorization, @PathVariable(value = "id") int id) {
	
            try{                
                service.deleteThreadById(id);
                return new ResponseEntity<>(HttpStatus.OK);
            
            }catch(Exception e){
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
            
	}
	
	//avoid infinite recursion
	private void modifyThread(Thread thread) {
		List<Post> posts = new ArrayList<>();
		for(Post post: thread.getPosts()) {
			posts.add(new Post(post.getId()));
		}
		thread.setPosts(posts);
		thread.setResearchProject(new ResearchProject(thread.getResearchProject().getId()));
	}
	
}
