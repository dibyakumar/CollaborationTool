package com.project.microservice.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.microservice.entity.Project;
import com.project.microservice.entity.Team;

public interface ProjectRepo extends JpaRepository<Project, Long>{


	Project findByTeam(Team team);

}
