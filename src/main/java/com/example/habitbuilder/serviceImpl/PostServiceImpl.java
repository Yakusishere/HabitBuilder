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

        QueryWrapper<Comment> wrapper = new QueryWrapper<>();
        wrapper.eq("postId",postId);
        commentMapper.delete(wrapper);//删掉帖子所有的评论
        //评论点赞关系
        QueryWrapper<Likecomments> wrapper1 = new QueryWrapper<>();
        wrapper1.eq("postId",postId);
        likecommentsMapper.delete(wrapper1);
        //帖子点赞关系
        QueryWrapper<Likepost> wrapper2 = new QueryWrapper<>();
        wrapper2.eq("postId",postId);
        likepostMapper.delete(wrapper2);
        //帖子收藏关系
        QueryWrapper<Collectpost> wrapper3 = new QueryWrapper<>();
        wrapper3.eq("postId",postId);
        collectpostMapper.delete(wrapper3);
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
}
