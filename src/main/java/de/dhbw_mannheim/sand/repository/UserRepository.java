package de.dhbw_mannheim.sand.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import de.dhbw_mannheim.sand.model.User;

public interface UserRepository extends JpaRepository<User, Integer> {

	User findByLogin(String login);

	User findByEmail(String email);
	
	User findById(int id);

}
