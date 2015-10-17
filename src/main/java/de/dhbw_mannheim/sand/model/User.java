package de.dhbw_mannheim.sand.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name="user")
@JsonInclude(value=Include.NON_NULL)
public class User extends LazyObject {

	private String login;

	@Column(name="password")
	private byte[] hashedPassword;

	@Transient
	private String password;

	private int iterations;

	private byte[] salt;

	private String firstname;

	private String lastname;

	private String title;

	private String email;

	private int deleted;
	
	public int getDeleted() {
		return deleted;
	}

	public void setDeleted(int deleted) {
		this.deleted = deleted;
	}

	@Transient
	private List<Role> roles = new ArrayList<Role>();

	@Transient
	private List<ResearchProject> researchProjects;

	public List<ResearchProject> getResearchProjects() {
		return researchProjects;
	}

	public void setResearchProjects(List<ResearchProject> researchProjects) {
		this.researchProjects = researchProjects;
	}

	public User(int id) {
		super(id);
		setIsLoaded(false);
	}

	public User() {
		super(-1);
		setIsLoaded(false);
	}

	@JsonCreator
	public User(@JsonProperty("id") Integer id, 
			@JsonProperty("login") String login,
			@JsonProperty("firstname") String firstname,
			@JsonProperty("lastname") String lastname,
			@JsonProperty("title") String title, 
			@JsonProperty("email") String email,
			@JsonProperty("password") String password) {
		super(id);
		this.login = login;
		this.firstname = firstname;
		this.lastname = lastname;
		this.title = title;
		this.email = email;
		this.password = password;
		setIsLoaded(true);
	}

	public User(int id, String login, byte[] hashedPassword, int iterations, byte[] salt, String firstname, String lastname,
			String title, String email) {
		super(id);
		this.login = login;
		this.hashedPassword = hashedPassword;
		this.iterations = iterations;
		this.salt = salt;
		this.firstname = firstname;
		this.lastname = lastname;
		this.title = title;
		this.email = email;
		setIsLoaded(true);
	}

	
	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	@JsonIgnore
	public byte[] getHashedPassword() {
		return hashedPassword;
	}

	public void setHashedPassword(byte[] hashedPassword) {
		this.hashedPassword = hashedPassword;
	}

	@JsonIgnore
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@JsonIgnore
	public int getIterations() {
		return iterations;
	}

	public void setIterations(int iterations) {
		this.iterations = iterations;
	}

	@JsonIgnore
	public byte[] getSalt() {
		return salt;
	}

	public void setSalt(byte[] salt) {
		this.salt = salt;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	@Override
	public boolean equals(Object obj) {

		return super.equals(obj);
	}

	@Override
	public int hashCode() {
		return super.hashCode();
	}

	@JsonIgnore
	public boolean isTeacher() {
		if (roles == null) {
			return false;
		}
		for (Role role : roles) {
			if (role instanceof Teacher && role.isValid()) {
				return true;
			}
		}

		return false;
	}

	@JsonIgnore
	public boolean isStudent() {
		if (roles == null) {
			return false;
		}
		for (Role role : roles) {
			if (role instanceof Student && role.isValid()) {
				return true;
			}
		}

		return false;
	}

	@JsonIgnore
	public boolean isSupervisor() {
		if (roles == null) {
			return false;
		}
		for (Role role : roles) {
			if (role instanceof Supervisor && role.isValid()) {
				return true;
			}
		}

		return false;
	}

	@JsonIgnore
	public boolean isSecretary() {
		if (roles == null) {
			return false;
		}
		for (Role role : roles) {
			if (role instanceof Secretary && role.isValid()) {
				return true;
			}
		}

		return false;

	}

	@JsonIgnore
	public boolean isAdmin() {
		if (roles == null) {
			return false;
		}
		for (Role role : roles) {
			if (role instanceof Admin && role.isValid()) {
				return true;
			}
		}

		return false;
	}

	/**
	 * Iterates over all user role objects and returns the internal role matching the supplied role if one is found.
	 * This cannot be achieved using the {@link List#contains(Object)} method since the role Id is not unique for all
	 * role types.
	 *
	 * @param role
	 *            The role to check for
	 * @return The matching internal role object or null
	 * @throws NullPointerException
	 *             if the user object is not fully loaded
	 */
	private Role getRole(Role role) {
		if (!getIsLoaded()) {
			throw new NullPointerException(
					"Please fix your code. The user object should not be lazily loaded when invoking hasRole/hasValidRole!");
		}
		for (Role userRole : roles) {
			boolean typeIsEqual = role.getClass().equals(userRole.getClass());
			if (typeIsEqual && role.equals(userRole)) {
				return userRole;
			}
		}
		return null;
	}

	@JsonIgnore
	public boolean hasRole(Role role) {
		return getRole(role) != null;
	}

	@JsonIgnore
	public boolean hasValidRole(Role role) {
		Role userRole = getRole(role);
		if (userRole != null && userRole.isValid()) {
			return true;
		}
		return false;
	}
}
