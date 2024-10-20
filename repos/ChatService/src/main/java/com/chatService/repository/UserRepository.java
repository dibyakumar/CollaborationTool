package com.chatService.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chatService.entity.User;

public interface UserRepository extends JpaRepository<User, Long>{

	User findByUserName(String username);

}
