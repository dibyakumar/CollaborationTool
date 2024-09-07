package com.user.microservice.Repository;

import org.springframework.data.jpa.repository.JpaRepository;


import com.user.microservice.entity.User;

public interface UserRepository extends JpaRepository<User, Long>{

	User findByUserName(String username);

}
