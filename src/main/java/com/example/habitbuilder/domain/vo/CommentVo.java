package com.example.habitbuilder.domain.vo;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class CommentVo {
	private Integer commentId;

	private Integer postId;

	private String content;

	private Integer userId;

	private String nickName;

	private String avatarImg;

	private LocalDate commentDate;

	private Integer likeCount;

	private Integer replyCount;

	private Boolean isLiked;

	private List<ReplyVo> replyVoList;
}
