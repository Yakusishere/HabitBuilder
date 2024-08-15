package com.example.habitbuilder.domain.vo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BrowseHistoryVo {
	private String title;

	private String nickName;

	private String image;

	private String avatarImg;

	/**
	 * 浏览时间
	 */
	private LocalDateTime browseTime;
}
