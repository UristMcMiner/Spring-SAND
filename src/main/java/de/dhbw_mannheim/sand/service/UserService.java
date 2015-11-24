package de.dhbw_mannheim.sand.service;

import java.util.List;

import de.dhbw_mannheim.sand.model.ResearchProject;
import de.dhbw_mannheim.sand.model.Role;
import de.dhbw_mannheim.sand.model.User;

public interface UserService {

	public List<Role> getRolesByUser(int id, boolean lazy);
	public List<ResearchProject> getProjectsByUser(int id, boolean lazy);
	public List<User> getAllUsers();  

    /**
     * Checks whether a user with a certain login name exists in the database. This method also checks for inactive
     * (deleted) users and will return true if a deleted user with the supplied login exists in the database.
     *
     * @param login
     * @return true if the user exists
     */
    public boolean hasUserWithLogin(String login);

    public User getUserById(int id);

    public User getUserByEmail(String email);

    public User getUserByLogin(String login);

    public User getUserByLoginAndPassword(String login, String password);

    public int addUser(User user);

    public void editUser(User user);

    public void deleteUserById(int id);

    boolean checkPassword(String password, User user);

    /**
     * Changes the password for the specified user
     *
     * @param user
     *            Id and cleartext password need to be set
     */
    public void changePassword(User user);

}
