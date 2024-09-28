package com.project.microservice.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.microservice.entity.Task;

public interface TaskRepo extends JpaRepository<Task, Long>{

}
