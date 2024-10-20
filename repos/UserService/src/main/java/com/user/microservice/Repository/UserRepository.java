package com.user.microservice.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.user.microservice.entity.User;

public interface UserRepository extends JpaRepository<User, Long> , JpaSpecificationExecutor<User>{

	User findByUserName(String username);

	List<User> findByOrganization(String organization);

}
