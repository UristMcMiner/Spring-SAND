package de.dhbw_mannheim.sand.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import de.dhbw_mannheim.sand.model.Role;
import de.dhbw_mannheim.sand.model.User;

public interface RoleRepository extends JpaRepository<Role, Integer>{

//	@Query("select r from Role r where r.user = ?1")
//	List<Role> findByUser(User user);

}
