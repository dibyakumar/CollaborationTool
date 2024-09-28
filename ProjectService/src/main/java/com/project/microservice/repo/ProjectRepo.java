package com.project.microservice.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.microservice.entity.Project;

public interface ProjectRepo extends JpaRepository<Project, Long>{

}
