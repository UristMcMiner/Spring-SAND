package de.dhbw_mannheim.sand.controller;

import de.dhbw_mannheim.sand.model.Post;
import de.dhbw_mannheim.sand.service.PostService;

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

@CrossOrigin
@RestController
@RequestMapping(value = "/sand/posts")
public class PostController {
	
	@Autowired
	private PostService service;
	
	/**
	 * REST-Endpoint for getting the Post with the id "id". The result is JSON-formated.
	 *
         * @param authorization
	 * @param id
         * @return 
	 *
	 */
	
//	@LoggedIn
	@RequestMapping(method = RequestMethod.GET, value = "/{id}")
	@ResponseBody
	public ResponseEntity<Post> getById(
                @RequestHeader(value="authorization", defaultValue="X") String authorization, 
                @PathVariable(value = "id") int id) {
                try {
			Post post = service.getPostById(id);
                        if (post != null) {
                            return new ResponseEntity<>(post, HttpStatus.OK);
                        } 
			
                        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	/**
	 * REST-Endpoint for adding a Post
	 *
         * @param authorization
         * @param post
         * @return 
	 */

	@RequestMapping(method = RequestMethod.POST, value="/")
	public ResponseEntity<Post> add(
                @RequestHeader(value="authorization", defaultValue="X") String authorization, 
                @RequestBody Post post) {
                int id = service.addPost(post);
		return new ResponseEntity<>(service.getPostById(id), HttpStatus.CREATED);
	}
	
	/**
	 * REST-Endpoint for editing a Post.
	 *
	 *
         * @param authorization
         * @param post
         * @return 
	 */
	
	@RequestMapping(method = RequestMethod.PUT, value = "/")
	public ResponseEntity<Post> edit(
                @RequestHeader(value="authorization", defaultValue="X") String authorization, 
                @RequestBody Post post) {
                    if (post.getHidden()) {
			try {
				service.deletePostById(post.getId());
			} catch (RuntimeException re) {
				return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
			}

                        return new ResponseEntity<>(service.getPostById(post.getId()), HttpStatus.OK);
		}

		// Post not allowed to edit
		return new ResponseEntity<>(HttpStatus.FORBIDDEN);
	}
	
	/**
	 * REST-Endpoint for deleting a Post.
	 *
         * @param authorization
         * @param id
         * @return 
	 */

	@RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
	public ResponseEntity<Post> delete(
            @RequestHeader(value="authorization", defaultValue="X") String authorization, 
            @PathVariable(value = "id") int id) {
                // not allowed, posts can only be hidden by putting a post with hidden = true	
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
	}
}
