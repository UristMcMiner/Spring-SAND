package de.dhbw_mannheim.sand.service;

import java.util.List;

import de.dhbw_mannheim.sand.model.Role;

public interface RoleService {

	public List<Role> getRolesByUserId(int userId);

	public Role getRoleById(int id);

	public int addRole(Role role);

	public void editRole(Role role);

	public void deleteRoleById(int id);

}
