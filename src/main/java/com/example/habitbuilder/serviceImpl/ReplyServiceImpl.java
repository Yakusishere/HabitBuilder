package com.example.habitbuilder.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.habitbuilder.domain.vo.ReplyVo;
import com.example.habitbuilder.mapper.CommentMapper;
import com.example.habitbuilder.mapper.UserMapper;
import com.example.habitbuilder.pojo.Comment;
import com.example.habitbuilder.pojo.Reply;
import com.example.habitbuilder.mapper.ReplyMapper;
import com.example.habitbuilder.pojo.User;
import com.example.habitbuilder.service.ILikereplyService;
import com.example.habitbuilder.service.IReplyService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.habitbuilder.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
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
	@Autowired
	private JwtUtil jwtUtil;
	@Autowired
	private CommentMapper commentMapper;

	@Override
	public List<ReplyVo> getReplyConversation(int replyId) {
		Reply rootReply = replyMapper.selectById(replyId);
		while (rootReply.getReplyToId() != null) {
			rootReply = replyMapper.selectById(rootReply.getReplyToId());
		}
		List<Reply> list = getRecursiveReplies(rootReply);
		List<ReplyVo> replyVoList = new ArrayList<>();
		for (Reply reply : list) {
			replyVoList.add(convertToReplyVo(reply.getUserId(), reply));
		}
		return replyVoList;
	}

	private List<Reply> getRecursiveReplies(Reply reply) {
		List<Reply> list = replyMapper.selectList(new LambdaQueryWrapper<Reply>()
				.eq(Reply::getReplyToId, reply.getReplyId()));
		for (Reply reply1 : list) {
			list.addAll(getRecursiveReplies(reply1));
		}
		return list;
	}

	@Override
	public ReplyVo addReply(String token, Reply reply) {
		int userId = jwtUtil.extractUserId(token);
		reply.setUserId(userId);
		reply.setReplyDate(LocalDate.now());
		reply.setPublishTime(LocalDateTime.now());
		replyMapper.insert(reply);
		Reply tmp = replyMapper.selectOne(new LambdaQueryWrapper<Reply>()
				.eq(Reply::getPublishTime, reply.getPublishTime()));
		// 更新replyCount
		synchronized (this) {
			Comment comment = commentMapper.selectById(reply.getCommentId());
			comment.setReplyCount(comment.getReplyCount() + 1);
			commentMapper.updateById(comment);
		}
		return convertToReplyVo(userId, tmp);
	}

	@Override
	public Boolean updateReply(String token, Reply reply) {
		int userId = jwtUtil.extractUserId(token);
		if (reply.getUserId() != userId)
			return false;
		replyMapper.updateById(reply);
		return true;
	}

	@Override
	public ReplyVo convertToReplyVo(int userId, Reply reply) {
		ReplyVo vo = new ReplyVo();
		User user = userMapper.selectById(reply.getUserId());
		User user2 = new User();
		if (reply.getReplyToId() != null) {
			Reply reply2 = replyMapper.selectById(reply.getReplyToId());
			user2 = userMapper.selectById(reply2.getUserId());
		} else {
			Comment comment = commentMapper.selectById(reply.getCommentId());
			user2 = userMapper.selectById(comment.getUserId());
		}
		vo.setReplyId(reply.getReplyId());
		vo.setCommentId(reply.getCommentId());
		vo.setReplyToId(reply.getReplyToId());
		vo.setContent(reply.getContent());
		vo.setSendUserId(reply.getUserId());
		vo.setSendNickName(user.getNickName());
		vo.setSendUserAvatarImg(user.getAvatarImg());
		vo.setReceiveUserId(user2.getUserId());
		vo.setReceiveNickName(user2.getNickName());
		vo.setReplyDate(reply.getReplyDate());
		vo.setReplyDate(reply.getReplyDate());
		vo.setLikeCount(reply.getLikeCount());
		vo.setIsLiked(Objects.equals(likereplyService.getIfLikeReply(userId, reply.getReplyId()), "true"));
		return vo;
	}
}
