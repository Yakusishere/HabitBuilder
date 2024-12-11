package com.example.habitbuilder.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class EventVo {
	private Integer eventId;

	private Integer planId;

	private String title;

	private String description;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm")
	private LocalDateTime startTime;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm")
	private LocalDateTime endTime;

	private LocalDate executionDate;

	private Boolean isCompleted;
}
