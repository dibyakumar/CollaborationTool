package com.chatService.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.chatService.entity.Team;

import feign.Param;

public interface TeamRepository extends JpaRepository<Team, Long>{

	Team findByTeamName(String teamName);

	@Query("SELECT t FROM Team t JOIN t.members m WHERE m.id = :userId")
	Team findByUserId(@Param("userId") Long userId);

}
