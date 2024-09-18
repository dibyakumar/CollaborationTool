package com.project.microservice.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.microservice.model.ProjectDto;
import com.project.microservice.model.SuccessResponse;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/project")
public class ProjectController {
	
	@PostMapping("/createProj")
	public ResponseEntity<SuccessResponse> createProject(@RequestBody ProjectDto projectDto) {
		return entity;
	}
	
}
