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

import de.dhbw_mannheim.sand.annotations.LoggedIn;
import de.dhbw_mannheim.sand.annotations.RuntimeBroker;
import de.dhbw_mannheim.sand.model.Course;
import de.dhbw_mannheim.sand.model.Student;
import de.dhbw_mannheim.sand.model.User;
import de.dhbw_mannheim.sand.service.RoleService;
import de.dhbw_mannheim.sand.service.StudentService;

@CrossOrigin
@RestController
@RequestMapping(value = "/sand/student")
public class StudentController {

	@Autowired
	private StudentService service;
	
	@RequestMapping(method = RequestMethod.GET, value = "/{id}")
	public ResponseEntity<Student> getStudentById(
			@RequestHeader(value="authorization", defaultValue="X") String authorization, 
			@PathVariable(value = "id") int id) {
		Student student = getStudent(id);
		if (student != null) {
			return new ResponseEntity<Student>(student, HttpStatus.OK);
		} else {
			return new ResponseEntity<Student>(HttpStatus.NOT_FOUND);
		}
	}
	
	@RequestMapping(method=RequestMethod.POST)
	public ResponseEntity<Student> add(@RequestBody Student student) {
		try {
			student.setStartDate(new java.sql.Date(student.getStartDate().getTime()));
			student.setEndDate(new java.sql.Date(student.getEndDate().getTime()));
			student.setId(null);
			int id = service.addRole(student);
			student = getStudent(id);
			return new ResponseEntity<Student>(student, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Student>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(method=RequestMethod.PUT)
	public ResponseEntity<Student> edit(@RequestBody Student student) {
		try {
			service.editRole(student);
			Student studentNew = getStudent(student.getId());
			return new ResponseEntity<Student>(studentNew, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Student>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
	public ResponseEntity<String> delete(@PathVariable(value="id") int id) {
		try {
			service.deleteRoleById(id);
			return new ResponseEntity<String>("true", HttpStatus.OK);
		} catch (RuntimeException e) {
			return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	private Student getStudent(Integer id) {
		Student student = (Student) service.getRoleById(id);
//		System.out.println(student.getStartDate());
//		System.out.println(student.getEndDate());
		if (student != null) {
			student.setCourse(new Course(1));
			student.setUser(new User(student.getUser().getId()));
		}
		return student;
	}
	
}
