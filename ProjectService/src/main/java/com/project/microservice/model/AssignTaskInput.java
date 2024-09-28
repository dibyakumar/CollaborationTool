package com.project.microservice.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AssignTaskInput {
	private Long userId;
	private Long taskId;
	private Long projectId;
	private Long teamId;
}
