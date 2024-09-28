package com.project.microservice.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.microservice.entity.AssignedTask;

public interface AssignedTaskRepo extends JpaRepository<AssignedTask, Long>{

}
