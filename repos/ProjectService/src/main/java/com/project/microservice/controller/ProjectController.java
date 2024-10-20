package com.project.microservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.benmanes.caffeine.cache.Cache;
import com.project.microservice.model.AssignTaskInput;
import com.project.microservice.model.ProjectDto;
import com.project.microservice.model.SuccessResponse;
import com.project.microservice.model.TaskDto;
import com.project.microservice.service.ProjectService;
import com.project.microservice.service.TaskService;
import com.project.microservice.util.ProjectUtil;

import jakarta.servlet.http.HttpServletRequest;


@RestController
@RequestMapping("/v1/project")
public class ProjectController {
	
	@Autowired
	private ProjectService projectService;
	
	@Autowired
	private TaskService taskService;
	
	@Autowired
	private Cache<String, String> localCahe;
	
	@Autowired
	private ProjectUtil projectUtil;
	
	@PostMapping("/create")
	public ResponseEntity<SuccessResponse> createProject(@RequestBody ProjectDto projectDto) {
		return ResponseEntity.status(200).body(projectService.createProject(projectDto));
	}
	
	@PostMapping("/addMemeber")
	public ResponseEntity<SuccessResponse> addTeamMember(@RequestParam String memberName,@RequestParam String teamId,HttpServletRequest request) {
		// store the users token in cache for later use
		String header = request.getHeader("Authorization");
		if(header.contains("Bearer ")) {
			localCahe.put(projectUtil.getCurrentUsername(),header);
		}
		return ResponseEntity.ok(projectService.addTeamMemeber(memberName,teamId)) ;
	}
	
	@PostMapping("/createTask")
	public ResponseEntity<SuccessResponse> createTask(@RequestBody TaskDto task){
		return ResponseEntity.ok(taskService.createTask(task));
	}
	
	@PostMapping("/assignTask")
	public ResponseEntity<SuccessResponse> assignTask(@RequestBody AssignTaskInput assignedTaskInput){
		return ResponseEntity.ok(taskService.assignTask(assignedTaskInput));
	}
	
	@PostMapping("/updateProject")
	public ResponseEntity<SuccessResponse> updateProject(@RequestBody ProjectDto projectDto){
		return ResponseEntity.ok(projectService.updateProject(projectDto));
	}
	
	@GetMapping("/userProjects")
	public ResponseEntity<List<ProjectDto>> getUserProjects() {
		return ResponseEntity.ok(projectService.getProjectsForUser());
	}
}
