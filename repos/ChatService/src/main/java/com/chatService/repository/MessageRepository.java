package com.chatService.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chatService.entity.Message;

public interface MessageRepository extends JpaRepository<Message, Long>{

	List<Message> findByTeamId(String teamId);

}
