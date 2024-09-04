package com.example.habitbuilder.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.habitbuilder.domain.vo.CommentVo;
import com.example.habitbuilder.domain.vo.ReplyVo;
import com.example.habitbuilder.mapper.*;
import com.example.habitbuilder.pojo.*;
import com.example.habitbuilder.service.ICommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.habitbuilder.service.ILikecommentsService;
import com.example.habitbuilder.service.IReplyService;
import com.example.habitbuilder.utils.LoginHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author 实训小组
 * @since 2024-07-03
 */
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements ICommentService {
	@Autowired
	private CommentMapper commentMapper;

	@Autowired
	private LikecommentsMapper likecommentsMapper;

	@Autowired
	private PostMapper postMapper;
	@Autowired
	private UserMapper userMapper;
	@Autowired
	private ReplyMapper replyMapper;
	@Autowired
	private IReplyService replyService;
	@Autowired
	private LoginHelper loginHelper;
	@Autowired
	private ILikecommentsService likecommentsService;

	@Override
	public List<CommentVo> getPostCommentSection(int postId) {
		List<Comment> commentList = commentMapper.selectList(new LambdaQueryWrapper<Comment>().eq(Comment::getPostId, postId));
		List<CommentVo> commentVoList = new ArrayList<>();
		for (Comment comment : commentList) {
			commentVoList.add(ConvertToCommentVo(comment.getUserId(), comment));
		}
		return commentVoList;
	}

	@Override
	public List<CommentVo> listByUserId(String token) {
		int userId = loginHelper.getUserId(token);
		List<Comment> commentList = commentMapper.selectList(new LambdaQueryWrapper<Comment>().eq(Comment::getUserId, userId));
		List<CommentVo> commentVoList = new ArrayList<>();
		for (Comment comment : commentList) {
			commentVoList.add(ConvertToCommentVo(comment.getUserId(), comment));
		}
		return commentVoList;
	}

	@Override
	public CommentVo addComment(String token, Comment comment) {
		comment.setUserId(loginHelper.getUserId(token));
		comment.setCommentDate(LocalDate.now());
		comment.setPublishTime(LocalDateTime.now());
		commentMapper.insert(comment);
		Comment tmp = commentMapper.selectOne(new LambdaQueryWrapper<Comment>()
				.eq(Comment::getPublishTime, comment.getPublishTime()));
		return ConvertToCommentVo(tmp.getUserId(), comment);
	}

	@Override
	public void deleteComment(int commentId) {
		commentMapper.deleteById(commentId);
	}

	@Override
	public CommentVo ConvertToCommentVo(int userId, Comment comment) {
		CommentVo vo = new CommentVo();
		User user = userMapper.selectOne(new LambdaQueryWrapper<User>().eq(User::getUserId, comment.getUserId()));
		vo.setCommentId(comment.getCommentId());
		vo.setPostId(comment.getPostId());
		vo.setContent(comment.getContent());
		vo.setUserId(comment.getUserId());
		vo.setNickName(user.getNickName());
		vo.setAvatarImg(user.getAvatarImg());
		vo.setCommentDate(comment.getCommentDate());
		vo.setLikeCount(comment.getLikeCount());
		vo.setReplyCount(comment.getReplyCount());
		vo.setIsLiked(likecommentsService.getIfLikeComment(userId, comment.getCommentId()));
		List<Reply> replyList = replyMapper.selectList(new LambdaQueryWrapper<Reply>().eq(Reply::getCommentId, comment.getCommentId()));
		List<ReplyVo> replyVoList = new ArrayList<>();
		for (Reply reply : replyList) {
			replyVoList.add(replyService.convertToReplyVo(userId, reply));
		}
		vo.setReplyVoList(replyVoList);
		return vo;
	}
}
