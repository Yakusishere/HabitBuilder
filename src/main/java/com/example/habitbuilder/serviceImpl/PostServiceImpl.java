package com.example.habitbuilder.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.habitbuilder.domain.PageQuery;
import com.example.habitbuilder.domain.vo.PostVo;
import com.example.habitbuilder.mapper.*;
import com.example.habitbuilder.pojo.*;
import com.example.habitbuilder.service.IPostService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author 实训小组
 * @since 2024-07-03
 */
@Service
public class PostServiceImpl extends ServiceImpl<PostMapper, Post> implements IPostService {
	@Autowired
	private PostMapper postMapper;
	@Autowired
	private UserMapper userMapper;
	@Autowired
	private CommentMapper commentMapper;

	@Override
	public List<PostVo> getPostList(Post post, PageQuery pageQuery) {
		LambdaQueryWrapper<Post> lqw = Wrappers.lambdaQuery();
		lqw.eq(post.getPostId() != null, Post::getPostId, post.getPostId());
		lqw.eq(post.getUserId() != null, Post::getUserId, post.getUserId());
		lqw.like(post.getTitle() != null, Post::getTitle, post.getTitle());
		Page<Post> page = postMapper.selectPage(pageQuery.build(), lqw);
		List<Post> posts = page.getRecords();
		List<PostVo> voList=null;
		return voList;
	}

	@Override
	public void addPost(Post post) {
		postMapper.insert(post);
	}

	@Override
	public void deletePost(int postId) {
		postMapper.deleteById(postId);
	}

	@Override
	public void updatePost(Post post) {

		QueryWrapper<Post> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("postId", post.getPostId());
		postMapper.update(post, queryWrapper);
	}

	@Override
	public List<Post> searchPost(String title) {
		QueryWrapper<Post> queryWrapper = new QueryWrapper<>();
		queryWrapper.like("title", title);
		return postMapper.selectList(queryWrapper);
	}

	@Override
	public List<Post> getPostByUserId(int userId) {
		QueryWrapper<Post> queryWrapper = new QueryWrapper<>();
		queryWrapper.like("userId", userId);
		return postMapper.selectList(queryWrapper);
	}


	public PostVo ConvertToVo(Post post) {
		PostVo vo = new PostVo();
		User user = userMapper.selectById(post.getUserId());
		vo.setPostId(post.getPostId());
		vo.setUserId(post.getUserId());
		vo.setNickName(user.getNickName());
		vo.setAvatarImg(user.getAvatarImg());
		vo.setIsViewable(post.getIsViewable());
		vo.setTitle(post.getTitle());
		vo.setContent(post.getContent());
		vo.setPublishDate(post.getPublishDate());
		vo.setLikeCount(post.getLikeCount());
		vo.setFavCount(post.getFavCount());
		vo.setCommentCount(post.getCommentCount());

		List<Comment> commentList = commentMapper.selectList(new LambdaQueryWrapper<Comment>().eq(Comment::getPostId, post.getPostId()));
		for (int i = 0; i < commentList.size(); i++) {

		}

		return vo;
	}
}
