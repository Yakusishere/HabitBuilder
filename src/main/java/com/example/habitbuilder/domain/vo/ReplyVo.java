package com.example.habitbuilder.domain.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;

import java.time.LocalDate;

public class ReplyVo {
	private Integer commentId;

	private Integer postId;

	private String content;

	private Integer sendUserId;

	private String sendNickName;

	private String sendUserAvatarImg;

	private Integer receiveUserId;

	private String receiveNickName;

	private LocalDate commentDate;

	private Integer likeCount;
}
