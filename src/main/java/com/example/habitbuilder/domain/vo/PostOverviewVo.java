package com.example.habitbuilder.domain.vo;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class PostOverviewVo {
	private Integer postId;

	private Integer userId;

	private String nickName;

	private String avatarImg;

	private Boolean isViewable;

	private String title;

	private LocalDate publishDate;

	private Integer likeCount;

	private Integer favCount;

	private String OverviewImage;
}
