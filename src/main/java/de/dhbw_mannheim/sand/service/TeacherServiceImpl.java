package de.dhbw_mannheim.sand.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import de.dhbw_mannheim.sand.model.Role;
import de.dhbw_mannheim.sand.model.Teacher;
import de.dhbw_mannheim.sand.model.User;
import de.dhbw_mannheim.sand.repository.TeacherRepository;
import de.dhbw_mannheim.sand.repository.UserRepository;

@Service
public class TeacherServiceImpl extends RoleServiceImpl implements
		TeacherService {

	@Autowired
	private TeacherRepository teacher_rep;
	
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public List<Role> getRolesByUserId(int userId) {
		User user = userRepository.findOne(userId);
		if (user == null) {
			return new ArrayList<Role>();
		}
		return teacher_rep.findByUser(user);
	}
	
	@Override
	public Role getRoleById(int id) {
		return teacher_rep.findOne(id);
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public int addRole(Role role) {
		role = teacher_rep.save((Teacher)role);
		return role.getId();
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public void editRole(Role role) {
		teacher_rep.save((Teacher)role);
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public void deleteRoleById(int id) {
		Role role = teacher_rep.findOne(id);
		if (role != null) {
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.DATE, -1);
			Date yesterday = new java.sql.Date(cal.getTimeInMillis());
			((Role) role).setEndDate(yesterday);
		}
		else
			throw new RuntimeException("Teacher does not exist");
	}



}
