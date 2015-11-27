package de.dhbw_mannheim.sand.service;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import de.dhbw_mannheim.sand.model.ResearchProject;
import de.dhbw_mannheim.sand.model.ResearchProjectOffer;
import de.dhbw_mannheim.sand.model.Role;
import de.dhbw_mannheim.sand.model.User;
import de.dhbw_mannheim.sand.repository.ResearchProjectOfferRepository;
import de.dhbw_mannheim.sand.repository.StudentRepository;
import de.dhbw_mannheim.sand.repository.UserRepository;
import de.dhbw_mannheim.sand.repository.TeacherRepository;

@Service
public class UserServiceImpl implements UserService {

	private Logger logger = Logger.getLogger(UserServiceImpl.class);

	@Autowired
	private UserRepository repository;
	
	@Autowired
	private StudentRepository studentRepository;
	
	@Autowired
	private TeacherRepository teacherRepository;
	
	@Autowired
	private ResearchProjectOfferRepository researchProjectRepository;
	
	@Override
	public List<User> getAllUsers() {
		List<User> result = new ArrayList<User>();
		for (User user: repository.findAll()) {
			if (user.getDeleted()==0) {
				result.add(user);
			}
		}
		return result;
	}

	@Override
	public boolean hasUserWithLogin(String login) {
		return repository.findByLogin(login)!=null;
	}

	@Override
	public User getUserById(int id) {
		return modifyUser(repository.findById(id));
	}

	@Override
	public User getUserByEmail(String email) {
		return modifyUser(repository.findByEmail(email));
	}

	@Override
	public User getUserByLogin(String login) {
		return modifyUser(repository.findByLogin(login));
	}

	@Override
	public User getUserByLoginAndPassword(String login, String password) {
		User user = getUserByLogin(login);
		if (checkPassword(password, user)) {
			return user;
		} else {
			return null;
		}
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public int addUser(User user) {
        user.setIterations(10000);
        byte[] salt;
        salt = generateSalt();
        user.setSalt(salt);
        user.setHashedPassword(hashPassword(user.getPassword(), 10000, salt));
		user = repository.save(user);
		return user.getId();
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public void editUser(User user) {
		User userInDB = repository.findOne(user.getId());
		if (checkPassword(user.getPassword(), userInDB)) {
            user.setHashedPassword(userInDB.getHashedPassword());
            user.setIterations(userInDB.getIterations());
            user.setSalt(userInDB.getSalt());
            user.setDeleted(0);
			repository.saveAndFlush(user);
		} else {
			throw new RuntimeException();
		}
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public void deleteUserById(int id) {
		User user = repository.findOne(id);
		if (user != null) {
			user.setDeleted(1);
			repository.flush();
		} else {
			throw new RuntimeException();
		}
	}

	@Override
	public boolean checkPassword(String password, User user) {
        byte[] passwordHash = null;
        passwordHash = hashPassword(password, user.getIterations(), user.getSalt());
        return Arrays.equals(passwordHash, user.getHashedPassword());
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public void changePassword(User user) {
		User userInDB = repository.findOne(user.getId());
		if (!checkPassword(userInDB.getPassword(), user)) {
			userInDB.setHashedPassword(hashPassword(user.getPassword(), user.getIterations(), user.getSalt()));
			repository.flush();
		}
	}

	@Override
	public List<Role> getRolesByUser(int id, boolean lazy) {

		List<Role> roles = new ArrayList<Role>();
		List<Role> roleTeacher= new ArrayList<Role>();
		List<Role> roleStudent =  new ArrayList<Role>();
		User user = repository.findOne(id);
		roleTeacher = teacherRepository.findByUser(user);
		for(Role role: roleTeacher){
			roles.add(role);
		}
		roleStudent = studentRepository.findByUser(user);
		for(Role role: roleStudent){
			roles.add(role);
		}
		return roles;
	}

	@Override
	public List<ResearchProject> getProjectsByUser(int id, boolean lazy) {
		List<ResearchProject> resultList = new ArrayList<ResearchProject>();
		List<ResearchProjectOffer> projectsByCreator = new ArrayList<ResearchProjectOffer>();
		List<ResearchProjectOffer> projectsByUser = new ArrayList<ResearchProjectOffer>();
		User user = repository.findById(id);
		projectsByCreator = researchProjectRepository.findByCreator(user);
		for(ResearchProjectOffer project : projectsByCreator){
			resultList.add(project);
		}
		projectsByUser = researchProjectRepository.findByInterestedUser(user);
		for(ResearchProjectOffer project : projectsByUser){
			resultList.add(project);
		}
		return resultList;
	}

    private byte[] hashPassword(String password, int iterations, byte[] salt) {
            String algorithm = "PBKDF2WithHmacSHA1";
            int hashLength = 160;
            KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, iterations, hashLength);
            try {
                    SecretKeyFactory f = SecretKeyFactory.getInstance(algorithm);
                    return f.generateSecret(spec).getEncoded();
            } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
                    // If this error occurs something is seriously wrong
                    logger.error("An error occured while hashing a user password: "+e);
                    throw new RuntimeException(e);
            }
    }

    private byte[] generateSalt() {
            try {
                    SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
                    byte[] salt = new byte[16];
                    random.nextBytes(salt);
                    return salt;
            } catch (NoSuchAlgorithmException e) {
                    // If this error occurs something is seriously wrong
                    logger.error("The required hashing algorithm could not be found", e);
                    throw new RuntimeException(e);
            }
    }
    
    private User modifyUser(User user) {
		if ((user != null) && (user.getDeleted()==0)) {
			user.setRoles(getRolesByUser(user.getId(), false));

			List<ResearchProjectOffer> projectsByCreator = researchProjectRepository.findByCreator(user);
			for (ResearchProject project: projectsByCreator) {
				project.setCreator(new User(project.getCreator().getId()));
			}
			List<ResearchProjectOffer> projectsByInterestedUser = researchProjectRepository.findByInterestedUser(user);
			for (ResearchProjectOffer project: projectsByInterestedUser) {
				project.setCreator(new User(project.getCreator().getId()));
				List<User> users = new ArrayList<>(); 
				for (User u: project.getUsers()) {
					users.add(new User(u.getId()));
				}
				project.setUsers(users);
			}
			List<ResearchProject> projects = new ArrayList<>();
			projects.addAll(projectsByCreator);
			projects.addAll(projectsByInterestedUser);
			user.setResearchProjects(projects);
			return user;
		} else {
			return null;
		}
    	
    }

}
