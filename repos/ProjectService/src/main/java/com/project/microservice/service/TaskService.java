package com.project.microservice.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;

import com.project.microservice.Exception.ServiceException;
import com.project.microservice.entity.AssignedTask;
import com.project.microservice.entity.Project;
import com.project.microservice.entity.ProjectRole;
import com.project.microservice.entity.Task;
import com.project.microservice.entity.Team;
import com.project.microservice.entity.User;
import com.project.microservice.model.AssignTaskInput;
import com.project.microservice.model.SuccessResponse;
import com.project.microservice.model.TaskDto;
import com.project.microservice.repo.AssignedTaskRepo;
import com.project.microservice.repo.ProjectRepo;
import com.project.microservice.repo.ProjectRoleRepository;
import com.project.microservice.repo.TaskRepo;
import com.project.microservice.repo.TeamRepository;
import com.project.microservice.repo.UserRepository;
import com.project.microservice.util.ProjectUtil;

import io.jsonwebtoken.lang.Arrays;

@Service
public class TaskService {
	@Autowired
	private TaskRepo taskRepo;

	@Autowired
	private ProjectRepo projectRepo;

	@Autowired
	private ProjectUtil projectUtil;

	@Autowired
	private UserRepository urepo;

	@Autowired
	private ProjectRoleRepository projectRoleRepo;

	@Autowired
	private AssignedTaskRepo assignedTaskRepo;

	@Autowired
	private TeamRepository teamRepo;

	private final List<String> adminRoles = Arrays.asList(new String[] { "ADMIN", "LEAD" });

	public SuccessResponse createTask(TaskDto task) {
		Optional<Project> project = projectRepo.findById(task.getProjectId());
		if (!project.isPresent()) {
			throw new ServiceException("project not found", HttpStatusCode.valueOf(400));
		}

		// only admin or project lead can create a task
		User user = urepo.findByUserName(projectUtil.getCurrentUsername());
		ProjectRole projectRole = projectRoleRepo.findByUserIdAndProjectId(user.getId(), project.get().getProjectId());
		if (!adminRoles.contains(projectRole.getRole().name())) {
			throw new ServiceException("User does not have required role", HttpStatusCode.valueOf(400));
		}

		Task newTask = new Task();
		newTask.setTaskName(task.getTaskName());
		newTask.setDescription(task.getDescription());
		newTask.setEndDate(task.getEndDate());
		newTask.setStatus(task.getStatus());
		newTask.setStartDate(task.getStartDate());
		newTask.setProjectId(project.get());

		taskRepo.save(newTask);
		return SuccessResponse.builder().message("Task Created").build();
	}

	public SuccessResponse assignTask(AssignTaskInput assignedTaskInput) {
		// validate
		validateAssignedTask(assignedTaskInput);

		AssignedTask assignedTask = new AssignedTask();
		assignedTask.setProjectId(assignedTaskInput.getProjectId());
		assignedTask.setTaskid(assignedTaskInput.getTaskId());
		assignedTask.setTeamId(assignedTaskInput.getTeamId());
		assignedTask.setUserId(assignedTaskInput.getUserId());

		assignedTaskRepo.save(assignedTask);

		// inform user to whom task is assigned
		Task task = taskRepo.findById(assignedTaskInput.getTaskId()).get();
		User user = urepo.findById(assignedTaskInput.getUserId()).get();
		projectUtil.notifyByMail(user.getEmail(), "New Task",
				projectUtil.generateHtmlContentForNewTaskNotify(user.getUserName(), task.getTaskName(),
						projectRepo.findById(assignedTaskInput.getProjectId()).get().getName(), task.getEndDate(),
						task.getDescription()));

		return SuccessResponse.builder().message("Task assigned").build();
	}

	private void validateAssignedTask(AssignTaskInput assignedTaskInput) {

		// validate all id
		Optional<User> user = urepo.findById(assignedTaskInput.getUserId());
		if (user.isEmpty())
			throw new ServiceException("user not found", HttpStatusCode.valueOf(400));
		Optional<Project> project = projectRepo.findById(assignedTaskInput.getProjectId());
		if (project.isEmpty())
			throw new ServiceException("project not found", HttpStatusCode.valueOf(400));
		Optional<Task> task = taskRepo.findById(assignedTaskInput.getTaskId());
		if (task.isEmpty())
			throw new ServiceException("task not found", HttpStatusCode.valueOf(400));
		Optional<Team> team = teamRepo.findById(assignedTaskInput.getTeamId());
		if (team.isEmpty())
			throw new ServiceException("team not found", HttpStatusCode.valueOf(400));
		// validate project and team
		if (!(project.get().getTeam().getTeam_id() == team.get().getTeam_id()))
			throw new ServiceException("project and team are not releated ", HttpStatusCode.valueOf(400));
		// validate team and user
		if (!team.get().getMembers().contains(user.get())) {
			throw new ServiceException("User Does not belong to the team", HttpStatusCode.valueOf(400));
		}
		// validate task and project
		if (!task.get().getProjectId().getProjectId().equals(project.get().getProjectId())) {
			throw new ServiceException("Task is not belong to the project", HttpStatusCode.valueOf(400));
		}

		// check logged in user role
		User loggedInuser = urepo.findByUserName(projectUtil.getCurrentUsername());
		ProjectRole projectRole = projectRoleRepo.findByUserIdAndProjectId(loggedInuser.getId(),
				project.get().getProjectId());
		if (null == projectRole || !adminRoles.contains(projectRole.getRole().name())) {
			throw new ServiceException("User does not have required role", HttpStatusCode.valueOf(400));
		}
	}

}
