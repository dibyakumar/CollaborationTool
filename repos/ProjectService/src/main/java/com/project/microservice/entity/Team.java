package com.project.microservice.entity;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name="Team")
public class Team {
	@Column(name="team_id")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long team_id;
	@Column(name="team_name")
	private String teamName;
	// many teams can have many users
	@ManyToMany(cascade = CascadeType.PERSIST,fetch = FetchType.LAZY)
	 @JsonBackReference
	@JoinTable(
	        name = "team_members", 
	        joinColumns = @JoinColumn(name = "team_id"), 
	        inverseJoinColumns = @JoinColumn(name = "user_id")
	    )
	private Set<User> members = new HashSet<>();
	 @ManyToMany(cascade = CascadeType.PERSIST,fetch = FetchType.LAZY)
	 @JsonBackReference
	    @JoinTable(
	        name = "team_admins", // Separate join table for admins
	        joinColumns = @JoinColumn(name = "team_id"),
	        inverseJoinColumns = @JoinColumn(name = "admin_id")
	    )
	    private Set<User> admins = new HashSet<>(); // Set of admins
	
}
