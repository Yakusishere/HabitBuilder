package com.example.habitbuilder.domain.bo;

import lombok.Data;

import java.time.LocalDate;

@Data
public class EventBo {
	private Integer planId;

	private Integer eventId;

	private Integer userId;

	private LocalDate date;
}
