package com.example.habitbuilder.domain.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.time.LocalDate;


@Data
public class ReplyVo {
	private Integer replyId;

	private Integer commentId;

	private Integer replyToId;

	private String content;

	private Integer sendUserId;

	private String sendNickName;

	private String sendUserAvatarImg;

	private Integer receiveUserId;

	private String receiveNickName;

	private LocalDate replyDate;

	private Integer likeCount;

	private Boolean isLiked;
}
