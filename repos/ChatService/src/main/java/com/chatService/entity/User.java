package com.chatService.entity;


import com.chatService.model.Role;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
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
	@Column(name="email")
	private String email;
	@Column(name="status")
	private String status;
	@Column(name="role")
	private Role role;
}
