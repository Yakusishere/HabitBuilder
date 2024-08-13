package com.example.habitbuilder.domain.vo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserVo {
	private Integer userId;

	private String userName;

	private String nickName;

	private String password;

	private String avatarImg;

	private LocalDateTime registerTime;

	private Integer myScore;
}
