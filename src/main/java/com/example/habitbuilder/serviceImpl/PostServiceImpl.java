package com.example.habitbuilder.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.habitbuilder.domain.PageQuery;
import com.example.habitbuilder.domain.vo.CommentVo;
import com.example.habitbuilder.domain.vo.PostOverviewVo;
import com.example.habitbuilder.domain.vo.PostVo;
import com.example.habitbuilder.mapper.*;
import com.example.habitbuilder.pojo.*;
import com.example.habitbuilder.service.ICollectpostService;
import com.example.habitbuilder.service.ICommentService;
import com.example.habitbuilder.service.ILikepostService;
import com.example.habitbuilder.service.IPostService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.habitbuilder.utils.LoginHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

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
	@Autowired
	private ICommentService commentService;
	@Autowired
	private LoginHelper loginHelper;
	@Autowired
	private ILikepostService likepostService;
	@Autowired
	private ICollectpostService collectpostService;

	@Override
	public List<PostOverviewVo> getPostList(Post post, PageQuery pageQuery) {
		LambdaQueryWrapper<Post> lqw = Wrappers.lambdaQuery();
		lqw.eq(post.getPostId() != null, Post::getPostId, post.getPostId());
		lqw.eq(post.getUserId() != null, Post::getUserId, post.getUserId());
		lqw.like(post.getTitle() != null, Post::getTitle, post.getTitle());
		Page<Post> page = postMapper.selectPage(pageQuery.build(), lqw);
		List<Post> posts = page.getRecords();
		List<PostOverviewVo> voList = new ArrayList<>();
		for (Post tmp : posts) {
			voList.add(ConvertToPostOverviewVo(tmp));
		}
		return voList;
	}

	@Override
	public PostVo browsePost(String token, int postId) {
		Post post = postMapper.selectById(postId);
		return ConvertToPostVo(loginHelper.getUserId(token), post);
	}

	@Override
	public void addPost(String token, Post post) {
		post.setUserId(loginHelper.getUserId(token));
		post.setPublishDate(LocalDate.now());
		postMapper.insert(post);
	}

	@Override
	public void deletePost(int postId) {
		postMapper.deleteById(postId);
	}

	@Override
	public void updatePost(String token, Post post) {
		LambdaQueryWrapper<Post> lqw = new LambdaQueryWrapper<>();
		lqw.eq(Post::getPostId, post.getPostId());
		if(loginHelper.getUserId(token)==postMapper.selectOne(lqw).getUserId()){
			postMapper.update(post, lqw);
		}
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

	public PostOverviewVo ConvertToPostOverviewVo(Post post) {
		PostOverviewVo vo = new PostOverviewVo();
		User user = userMapper.selectOne(new LambdaQueryWrapper<User>().eq(User::getUserId, post.getUserId()));
		vo.setPostId(post.getPostId());
		vo.setUserId(post.getUserId());
		vo.setNickName(user.getNickName());
		vo.setAvatarImg(user.getAvatarImg());
		vo.setIsViewable(post.getIsViewable());
		vo.setTitle(post.getTitle());
		vo.setPublishDate(post.getPublishDate());
		vo.setLikeCount(post.getLikeCount());
		String[] urls = post.getImage().split(",");
		String firstUrl = urls.length > 0 ? urls[0] : post.getImage();
		vo.setOverviewImage(firstUrl);
		return vo;
	}

	public PostVo ConvertToPostVo(int userId, Post post) {
		PostVo vo = new PostVo();
		User user = userMapper.selectOne(new LambdaQueryWrapper<User>().eq(User::getUserId, post.getUserId()));
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
		vo.setIsLiked(Objects.equals(likepostService.getIfLikePost(userId, post.getPostId()), "true"));
		vo.setIsFav(Objects.equals(collectpostService.getPostCollections(userId, post.getPostId()), "true"));
		vo.setImages(List.of(post.getImage().split(",")));

		List<Comment> commentList = commentMapper.selectList(new LambdaQueryWrapper<Comment>().eq(Comment::getPostId, post.getPostId()));
		List<CommentVo> commentVoList = new ArrayList<>();
		for (Comment comment : commentList) {
			commentVoList.add(commentService.ConvertToCommentVo(userId, comment));
		}
		vo.setCommentVoList(commentVoList);
		return vo;
	}
}
