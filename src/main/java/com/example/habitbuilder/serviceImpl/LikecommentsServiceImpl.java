package com.example.habitbuilder.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.habitbuilder.mapper.CommentMapper;
import com.example.habitbuilder.pojo.Comment;
import com.example.habitbuilder.pojo.Likecomments;
import com.example.habitbuilder.mapper.LikecommentsMapper;
import com.example.habitbuilder.pojo.Likepost;
import com.example.habitbuilder.service.ILikecommentsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.habitbuilder.utils.LoginHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author 实训小组
 * @since 2024-07-06
 */
@Service
public class LikecommentsServiceImpl extends ServiceImpl<LikecommentsMapper, Likecomments> implements ILikecommentsService {
	@Autowired
	private LikecommentsMapper likecommentsMapper;

	@Autowired
	private CommentMapper commentMapper;
	@Autowired
	private LoginHelper loginHelper;

	@Override
	public Boolean addLikeComment(String token, int commentId) {
		int userId = loginHelper.getUserId(token);
		if (likecommentsMapper.exists(new LambdaQueryWrapper<Likecomments>()
				.eq(Likecomments::getUserId, userId)
				.eq(Likecomments::getCommentId, commentId))) {
			return false;
		}
		synchronized (this) {
			Comment comment = commentMapper.selectById(commentId);
			comment.setLikeCount(comment.getLikeCount() + 1);
			commentMapper.updateById(comment);
		}
		Likecomments likecomments = new Likecomments();
		likecomments.setUserId(userId);
		likecomments.setCommentId(commentId);
		likecommentsMapper.insert(likecomments);
		return true;
	}

	@Override
	public Boolean deleteLikeComment(String token, int commentId) {
		int userId = loginHelper.getUserId(token);
		Likecomments likecomments = likecommentsMapper.selectOne(new LambdaQueryWrapper<Likecomments>()
				.eq(Likecomments::getCommentId, commentId)
				.eq(Likecomments::getUserId, userId));
		if (likecomments == null) {
			return false;
		}

		synchronized (this) {
			Comment comment = commentMapper.selectById(commentId);
			comment.setLikeCount(comment.getLikeCount() - 1);
			commentMapper.updateById(comment);
		}

		commentMapper.deleteById(likecomments);
		return true;
	}

	public Boolean getIfLikeComment(int userId, int commentId) {
		QueryWrapper<Likecomments> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("userId", userId);
		queryWrapper.eq("commentId", commentId);
		return !likecommentsMapper.selectList(queryWrapper).isEmpty();
	}


	public List<Likecomments> getLikeComment(String token) {
		QueryWrapper<Likecomments> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("userId", loginHelper.getUserId(token));
		return likecommentsMapper.selectList(queryWrapper);
	}

}
