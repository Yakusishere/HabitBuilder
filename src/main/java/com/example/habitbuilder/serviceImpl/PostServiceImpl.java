package com.example.habitbuilder.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.habitbuilder.mapper.*;
import com.example.habitbuilder.pojo.*;
import com.example.habitbuilder.service.IPostService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
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
    private CommentMapper commentMapper;

    @Autowired
    private LikecommentsMapper likecommentsMapper;

    @Autowired
    private LikepostMapper likepostMapper;

    @Autowired
    private CollectpostMapper collectpostMapper;

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
    public List<Post> getAllPost() {
        List<Post> posts = postMapper.selectList(null);
        posts.sort((t1,t2)->t2.getPostId().compareTo(t1.getPostId())); //按序号排序 最新的在前面
        return posts;
    }

    @Override
    public List<Post> searchPost(String title) {
        QueryWrapper <Post> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("title", title);
        return postMapper.selectList(queryWrapper);
    }

    @Override
    public List<Post> getPostByUserId(int userId) {
        QueryWrapper <Post> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("userId", userId);
        return postMapper.selectList(queryWrapper);
    }
}
