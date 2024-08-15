package com.example.habitbuilder.service;

import com.example.habitbuilder.domain.vo.ReplyVo;
import com.example.habitbuilder.pojo.Likereply;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.habitbuilder.pojo.Reply;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 实训小组
 * @since 2024-08-15
 */
public interface ILikereplyService extends IService<Likereply> {
	String getIfLikeReply(int userId, int replyId);
}
