package com.project.microservice.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.project.microservice.entity.ProjectRole;

import jakarta.websocket.server.PathParam;

public interface ProjectRoleRepository extends JpaRepository<ProjectRole, Long>{

	@Query(value="select * from project_role where project_id = :projectId and user_id = :userId",nativeQuery = true)
	ProjectRole findByUserIdAndProjectId(@PathParam("userId") Long userId,@PathParam("projectId") Long projectId);

	@Query(value="select * from project_role where user_id = :id",nativeQuery = true)
	List<ProjectRole> findByUserId(@PathParam("id") Long id);

}