package com.project.microservice.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.project.microservice.entity.Team;

import jakarta.websocket.server.PathParam;

public interface TeamRepository extends JpaRepository<Team, Long>{

//	@Query("SELECT t FROM Team t LEFT JOIN FETCH t.members LEFT JOIN FETCH t.admins WHERE t.team_id = :teamId")
//	Optional<Team> findById(@PathParam("teamId") Long teamId);
}
