package com.example.habitbuilder.service;

import com.example.habitbuilder.domain.vo.ReplyVo;
import com.example.habitbuilder.pojo.Reply;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 实训小组
 * @since 2024-08-15
 */
public interface IReplyService extends IService<Reply> {
	List<ReplyVo> getReplyConversation(int replyId);

	void addReply(String token, Reply reply);

	Boolean updateReply(String token, Reply reply);

	ReplyVo convertToReplyVo(int userId, Reply reply);
}
