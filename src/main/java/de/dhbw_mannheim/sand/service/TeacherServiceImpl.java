package de.dhbw_mannheim.sand.service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import de.dhbw_mannheim.sand.model.Role;
import de.dhbw_mannheim.sand.model.Student;
import de.dhbw_mannheim.sand.model.User;
import de.dhbw_mannheim.sand.repository.TeacherRepository;
import de.dhbw_mannheim.sand.repository.UserRepository;

@Service
public class TeacherServiceImpl extends RoleServiceImpl implements
		StudentService {

	@Autowired
	private TeacherRepository repository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public List<Role> getRolesByUserId(int userId) {
		User user = userRepository.findOne(userId);
		if (user == null) {
			return null;
		}
		return repository.findByUser(user);
	}
	
	@Override
	public Role getRoleById(int id) {
		return repository.findOne(id);
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public int addRole(Role role) {
		role = repository.save((Student)role);
		return role.getId();
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public void editRole(Role role) {
		repository.save((Student)role);
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public void deleteRoleById(int id) {
		Role role = repository.findOne(id);
		if (role != null) {
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.DATE, -1);
			Date yesterday = new java.sql.Date(cal.getTimeInMillis());
			((Role) role).setEndDate(yesterday);
		}
	}



}
