package com.project.microservice.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.project.microservice.constants.STATUS;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "Project")
@Data
public class Project {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "project_id")
	private Long projectId;
	@Column(name = "project_name")
	private String name;
	@Column(name = "description")
	private String description;
	@OneToOne
	@JoinColumn(name = "project_lead_id", referencedColumnName = "user_id")
	private User projectLead;

	// One-to-One with Team
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "team_id", referencedColumnName = "team_id")
	private Team team;

	@Column(name = "start")
	private LocalDate startDate;
	@Column(name = "end")
	private LocalDate endDate;
	@Column(name = "status")
	private STATUS status;
}
