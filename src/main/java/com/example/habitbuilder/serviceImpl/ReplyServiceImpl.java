package com.example.habitbuilder.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.habitbuilder.domain.vo.ReplyVo;
import com.example.habitbuilder.mapper.LikereplyMapper;
import com.example.habitbuilder.mapper.UserMapper;
import com.example.habitbuilder.pojo.Likereply;
import com.example.habitbuilder.pojo.Reply;
import com.example.habitbuilder.mapper.ReplyMapper;
import com.example.habitbuilder.pojo.User;
import com.example.habitbuilder.service.ILikereplyService;
import com.example.habitbuilder.service.IReplyService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author 实训小组
 * @since 2024-08-15
 */
@Service
public class ReplyServiceImpl extends ServiceImpl<ReplyMapper, Reply> implements IReplyService {
	@Autowired
	ReplyMapper replyMapper;
	@Autowired
	UserMapper userMapper;
	@Autowired
	ILikereplyService likereplyService;

	@Override
	public ReplyVo convertToReplyVo(int userId, Reply reply) {
		ReplyVo vo = new ReplyVo();
		Reply reply2 = replyMapper.selectById(reply.getReplyToId());
		User user = userMapper.selectById(reply.getUserId());
		User user2 = userMapper.selectById(reply2.getUserId());
		vo.setReplyId(reply.getReplyId());
		vo.setCommentId(reply.getCommentId());
		vo.setReplyToId(reply.getReplyToId());
		vo.setContent(reply.getContent());
		vo.setSendUserId(reply.getUserId());
		vo.setSendNickName(user.getNickName());
		vo.setSendUserAvatarImg(user.getAvatarImg());
		vo.setReceiveUserId(user2.getUserId());
		vo.setReceiveNickName(user2.getNickName());
		vo.setCommentDate(reply.getCommentDate());
		vo.setLikeCount(reply.getLikeCount());
		vo.setIsLiked(Objects.equals(likereplyService.getIfLikeReply(userId, reply.getReplyId()), "true"));
		return vo;
	}
}
