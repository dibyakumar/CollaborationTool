package com.project.microservice.model;

import java.time.LocalDate;

import com.project.microservice.constants.STATUS;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TaskDto {
	private Long projectId;
	private String taskName;
	private String description;
	private STATUS status;
	private LocalDate startDate;
	private LocalDate endDate;
}
