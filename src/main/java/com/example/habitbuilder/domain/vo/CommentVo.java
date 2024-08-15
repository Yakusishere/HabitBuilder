package com.example.habitbuilder.domain.vo;

import java.time.LocalDate;
import java.util.List;

public class CommentVo {
	private Integer commentId;

	private Integer postId;

	private String content;

	private Integer sendUserId;

	private String sendNickName;

	private String avatarImg;

	private LocalDate commentDate;

	private Integer likeCount;

	private Integer replyCount;

	private List<ReplyVo> replyVoList;
}
