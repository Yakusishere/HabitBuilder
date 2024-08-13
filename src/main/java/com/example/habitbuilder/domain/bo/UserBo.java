package com.example.habitbuilder.domain.bo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserBo {

	private Integer userId;

	private String userName;

	private String nickName;

	private String password;

	private String avatarImg;

	private LocalDateTime registerTime;

	private Integer myScore;
}
