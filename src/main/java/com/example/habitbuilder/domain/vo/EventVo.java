package com.example.habitbuilder.domain.vo;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class EventVo {
	private Integer eventId;

	private Integer planId;

	private String title;

	private String description;

	private Boolean isCompleted;

	private LocalDateTime startTime;

	private LocalDateTime endTime;

	private LocalDate executionDate;
}
