package com.project.microservice.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.project.microservice.constants.STATUS;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name="Project")
@Data
public class Project {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="project_id")
	private Long projectId;
	@Column(name="project_name")
	private String name;
	@Column(name="description")
	private String description;
	@OneToOne
	private Team team;
	@OneToOne
	private User projectLead;
	@Column(name="start")
	private LocalDate startDate;
	@Column(name="end")
	private LocalDate endDate;
	@Column(name="status")
	private STATUS status;
	@OneToMany(mappedBy = "projectId", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Task> tasks = new ArrayList<>();
}
