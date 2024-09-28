package com.project.microservice.entity;

import com.project.microservice.constants.PROJECTROLE;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name="project_role")
public class ProjectRole {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long proj_role_id;
	
	@ManyToOne
	@JoinColumn(name="user_id")
	private User userId;
	
	@ManyToOne
	@JoinColumn(name="project_id")
	private Project projectId;
	
	private PROJECTROLE role;
}
