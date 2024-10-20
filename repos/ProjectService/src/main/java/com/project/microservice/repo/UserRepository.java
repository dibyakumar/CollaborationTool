package com.project.microservice.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.microservice.entity.User;

public interface UserRepository extends JpaRepository<User, Long>{

	User findByUserName(String username);
	
}
