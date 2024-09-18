package com.project.microservice.entity;

import java.util.HashSet;
import java.util.Set;

import com.project.microservice.constants.Role;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name="user_details")
public class User {
	@Id
	@Column(name="user_id")
	private Long id;
	@Column(name="user_name")
	private String userName;
	@Column(name="password")
	private String password;
	@Column(name="role")
	private Role role;
	@Column(name="status")
	private String status;
	// many user can belong to many teams 
	@ManyToMany
    @JoinTable(
        name = "team_members", 
        joinColumns = @JoinColumn(name = "user_id"), 
        inverseJoinColumns = @JoinColumn(name = "team_id")
    )
	private Set<Team> teams = new HashSet<>();
	
	@ManyToMany(mappedBy = "admins")
	private Set<Team> managingTeams = new HashSet<>();
	@OneToOne(mappedBy = "projectLead")
	private Project projectLead;
}
