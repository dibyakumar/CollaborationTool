package com.project.microservice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name="assigned_task")
public class AssignedTask {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "assigned_task_id")
	private Long assignedTaskId;
	@Column(name="project_id")
	private Long projectId;
	@Column(name="task_id")
	private Long taskid;
	@Column(name="user_id")
	private Long userId;
	@Column(name="team_id")
	private Long teamId;
}
