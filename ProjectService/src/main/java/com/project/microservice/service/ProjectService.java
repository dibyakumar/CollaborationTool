package com.project.microservice.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;

import com.project.microservice.Exception.ServiceException;
import com.project.microservice.constants.PROJECTROLE;
import com.project.microservice.constants.STATUS;
import com.project.microservice.entity.Project;
import com.project.microservice.entity.ProjectRole;
import com.project.microservice.entity.Team;
import com.project.microservice.entity.User;
import com.project.microservice.model.ProjectDto;
import com.project.microservice.model.SuccessResponse;
import com.project.microservice.repo.ProjectRepo;
import com.project.microservice.repo.ProjectRoleRepository;
import com.project.microservice.repo.TeamRepository;
import com.project.microservice.repo.UserRepository;
import com.project.microservice.util.ProjectUtil;

@Service
public class ProjectService {
	
	@Autowired
	private UserRepository urepo;
	@Autowired
	private TeamRepository teamRepo;
	@Autowired
	private ProjectRepo projectrepo;
	@Autowired
	ProjectRoleRepository projectRoleRepo;
	@Autowired
	private ProjectUtil projUtil;

	public SuccessResponse createProject(ProjectDto projectDto) {
		Project project = new Project();
		project.setDescription(projectDto.getDescription());
		project.setEndDate(projectDto.getEndDate());
		project.setName(projectDto.getName());
		project.setStartDate(projectDto.getStartDate());
		project.setStatus(STATUS.IN_PROGRESS);

		User user = urepo.findByUserName(projUtil.getCurrentUsername());
		// whoever created project will be Project Lead initially
		project.setProjectLead(user);
		// create a team with only the Lead initially 
		Team team = new Team();
		team.setTeamName(project.getName()+"_Team"); // initially , later we can change the name
		// team admin
		team.getAdmins().add(user);
		team.getMembers().add(user); // only admin is there
		project.setTeam(team);
		
		// save team
		teamRepo.save(team);
		
		// save project
		projectrepo.save(project);
		
		
		// project role
		ProjectRole projectrole = new ProjectRole();
		projectrole.setProjectId(project);
		projectrole.setUserId(user);
		projectrole.setRole(PROJECTROLE.LEAD);
		
		projectRoleRepo.save(projectrole);
			
		return SuccessResponse.builder().message("Project Created").build();
	}

	public SuccessResponse addTeamMemeber(String memberName, String teamId) {
		User loggedInUser = urepo.findByUserName(projUtil.getCurrentUsername());
		Optional<Team> team = teamRepo.findById(Long.valueOf(teamId));
		if(!team.isPresent()) {
			throw new ServiceException("Team Does not exists !!", HttpStatusCode.valueOf(400));
		}
		// check the loggedin users is a team admin or not
		Set<User> admins = team.get().getAdmins();
		
		for(User admin : admins) {
			if(admin.getId() == loggedInUser.getId()) {
				User member = urepo.findByUserName(memberName);
				// check if the user exists or not
				if(null != member) {
					team.get().getMembers().add(member);
					teamRepo.save(team.get());
					projUtil.notifyByMail(member.getEmail(),"Welcome To The Team",projUtil.generateHtmlContentForNewTeamMember(memberName, team.get().getTeamName()));
					return SuccessResponse.builder().message("Team member Added SuccessFully").build();
				}
				else
					throw new ServiceException(memberName+" doesn't exists !!",HttpStatusCode.valueOf(400));
			}
			break;
		}
		
		throw new ServiceException("Something went wrong !!",HttpStatusCode.valueOf(400));
	}

	public SuccessResponse updateProject(ProjectDto projectDto) {
		
		Optional<Project> isproject = projectrepo.findById(Long.valueOf(projectDto.getProjectId()));
		if(!isproject.isPresent()) {
			throw new ServiceException("Project not found", HttpStatusCode.valueOf(400));
		}
		// only lead can update the project
				User currUser = urepo.findByUserName(projUtil.getCurrentUsername());
				ProjectRole role = projectRoleRepo.findByUserIdAndProjectId(currUser.getId(),Long.valueOf(projectDto.getProjectId()));
				if(null != role) {
					if(!role.getRole().equals(PROJECTROLE.LEAD)) {
						throw new ServiceException("Only Lead can update the project ", HttpStatusCode.valueOf(400));
					}
				}
		
		Project project = isproject.get();
		if(projectDto.getDescription()!=null || !projectDto.getDescription().isEmpty())
			project.setDescription(projectDto.getDescription());
		if(projectDto.getName() != null || !projectDto.getName().isEmpty())
			project.setName(projectDto.getName());
		if(projectDto.getStartDate() != null)
			project.setStartDate(projectDto.getStartDate());
		if(projectDto.getEndDate() != null)
			project.setEndDate(projectDto.getEndDate());
		if(projectDto.getStatus() != null)
			project.setStatus(projectDto.getStatus());
	
		projectrepo.save(project);
		return  SuccessResponse.builder().message("Project Updated").build();
	}

	public List<ProjectDto> getProjectsForUser() {
		List<ProjectDto> list = new ArrayList<>();
		List<ProjectRole> userProjectRole = projectRoleRepo.findByUserId(urepo.findByUserName(projUtil.getCurrentUsername()).getId());
		for(ProjectRole role: userProjectRole) {
			ProjectDto projdto = ProjectDto.builder().name(role.getProjectId().getName()).description(role.getProjectId().getDescription()).startDate(role.getProjectId().getStartDate())
			.endDate(role.getProjectId().getEndDate()).status(role.getProjectId().getStatus()).leadName(role.getProjectId().getProjectLead().getUserName()).build();
			list.add(projdto);
		}
		return list;
	}

	
}
