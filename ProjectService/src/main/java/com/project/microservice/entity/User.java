package com.project.microservice.entity;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
	@Column(name="email")
	private String email;
	@Column(name="status")
	private String status;
}
