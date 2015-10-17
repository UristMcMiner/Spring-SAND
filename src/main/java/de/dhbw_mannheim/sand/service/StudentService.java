package de.dhbw_mannheim.sand.service;

import java.util.List;

import de.dhbw_mannheim.sand.model.Role;

public interface StudentService extends RoleService {

	public List<Role> getRolesByUserId(int userId);

}
