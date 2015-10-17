package de.dhbw_mannheim.sand.service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import de.dhbw_mannheim.sand.model.Role;
import de.dhbw_mannheim.sand.model.User;
import de.dhbw_mannheim.sand.repository.RoleRepository;
import de.dhbw_mannheim.sand.repository.StudentRepository;
import de.dhbw_mannheim.sand.repository.UserRepository;

@Service
public class RoleServiceImpl implements RoleService {

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private UserRepository userRepository;
	
	@Override
	public List<Role> getRolesByUserId(int userId) {
		User user = userRepository.findOne(userId);
		if (user == null) {
			return null;
		}
		return null;
//		return roleRepository.findByUser(user);
	}

	@Override
	public Role getRoleById(int id) {
		return roleRepository.findOne(id);
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public int addRole(Role role) {
		role = roleRepository.save(role);
		return role.getId();
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public void editRole(Role role) {
		roleRepository.save(role);
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public void deleteRoleById(int id) {
		Role role = roleRepository.findOne(id);
		if (role != null) {
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.DATE, -1);
			Date yesterday = new java.sql.Date(cal.getTimeInMillis());
			((Role) role).setEndDate(yesterday);
		}
	}

}
