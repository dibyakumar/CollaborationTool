package com.project.microservice.model;

import java.time.LocalDate;

import com.project.microservice.constants.STATUS;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProjectDto {
	private String name;
	private String description;
	private LocalDate startDate;
	private LocalDate endDate;
	private STATUS status;
}
