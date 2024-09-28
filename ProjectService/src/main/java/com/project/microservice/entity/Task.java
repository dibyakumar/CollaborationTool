package com.project.microservice.entity;

import java.time.LocalDate;

import com.project.microservice.constants.STATUS;

import jakarta.persistence.Column;
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
@Table(name="task")
public class Task {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="task_id")
	private Long taskId;
	@ManyToOne
	@JoinColumn(name="project_id",referencedColumnName = "project_id")
	private Project projectId;
	@Column(name="task_name")
	private String taskName;
	@Column(name="description")
	private String description;
	@Column(name="status")
	private STATUS status;
	@Column(name="start")
	private LocalDate startDate;
	@Column(name="end")
	private LocalDate endDate;
	@Column(name="estimate_hours")
	private String estimateHours;
	@Column(name="actual_hours")
	private String actualHours;
}
