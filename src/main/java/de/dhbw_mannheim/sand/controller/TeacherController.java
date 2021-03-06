package de.dhbw_mannheim.sand.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import de.dhbw_mannheim.sand.model.Teacher;
import de.dhbw_mannheim.sand.model.User;
import de.dhbw_mannheim.sand.repository.TeacherRepository;
import de.dhbw_mannheim.sand.service.TeacherService;

@CrossOrigin
@RestController
@RequestMapping(value = "/sand/teachers")
public class TeacherController {

	@Autowired
	private TeacherRepository repository;
	
	@Autowired
	private TeacherService service;
	
	
	/**
	 * REST-Endpoint for getting a Teacher by its ID. The result is JSON-formated
	 *
	 * @return Teacher as JSON
	 *
	 */
	
//	@LoggedIn
	@RequestMapping(method = RequestMethod.GET, value = "/{id}")
//	@ResponseBody
	public ResponseEntity<Teacher> getById(@RequestHeader(value="authorization", defaultValue="X") String authorization, @PathVariable(value = "id") int id) {
		System.out.println(authorization + " " + id);
		Teacher teacher = null;
		if ((teacher = (Teacher)service.getRoleById(id)) != null) {
			modifyTeacher(teacher);
			return new ResponseEntity<Teacher>(teacher, HttpStatus.OK);
		} 
		else {
			return new ResponseEntity<Teacher>(HttpStatus.NOT_FOUND);
		}
	}
	
	/**
	 * REST-Endpoint for adding a Teacher.
	 *
	 * @param Teacher teacher
	 * @return added Teacher
	 *
	 */

//	@LoggedIn
	@RequestMapping(method = RequestMethod.POST, value="/")
//	@ResponseBody
	public ResponseEntity<Teacher> add(@RequestHeader(value="authorization", defaultValue="X") String authorization, @RequestBody Teacher teacher) {
		System.out.println(authorization + " " + teacher.getUser().getLogin());
		int id = service.addRole(teacher);
		Teacher added = (Teacher)service.getRoleById(id);
		modifyTeacher(added);
		return new ResponseEntity<Teacher>(added , HttpStatus.CREATED);
	}
	
	/**
	 * REST-Endpoint for editing a Teacher.
	 *
	 * @param Teacher teacher
	 * @return edited Teacher
	 *
	 */
	
//	@LoggedIn
	@RequestMapping(method = RequestMethod.PUT, value = "/")
//	@ResponseBody
	public ResponseEntity<Teacher> edit(@RequestHeader(value="authorization", defaultValue="X") String authorization, @RequestBody Teacher teacher) {
		System.out.println(authorization + " " + teacher.getId());
		try{
			service.editRole(teacher);
			Teacher edited = (Teacher)service.getRoleById(teacher.getId());
			modifyTeacher(edited);
			return new ResponseEntity<Teacher>(edited, HttpStatus.OK);
		}
		catch(RuntimeException e){
			return new ResponseEntity<Teacher>(HttpStatus.CONFLICT);
		}
	}
	
	/**
	 * REST-Endpoint for deleting a Teacher.
	 *
	 * @param int id
	 * @return String with value "true".
	 *
	 */
	
//	@LoggedIn
	@RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
//	@ResponseBody
	public ResponseEntity<String> delete(@RequestHeader(value="authorization", defaultValue="X") String authorization, @PathVariable(value = "id") int id) {
		System.out.println(authorization + " " + id);
		service.deleteRoleById(id);
		return new ResponseEntity<String>("true", HttpStatus.OK);
	}
	
	private void modifyTeacher(Teacher teacher) {
		teacher.setUser(new User(teacher.getUser().getId()));
	}
}
