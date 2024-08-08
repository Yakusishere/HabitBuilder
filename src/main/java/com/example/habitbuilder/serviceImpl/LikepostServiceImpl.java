package com.example.habitbuilder.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.habitbuilder.mapper.PostMapper;
import com.example.habitbuilder.pojo.Likepost;
import com.example.habitbuilder.mapper.LikepostMapper;
import com.example.habitbuilder.pojo.Post;
import com.example.habitbuilder.service.ILikepostService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 实训小组
 * @since 2024-07-06
 */
@Service
public class LikepostServiceImpl extends ServiceImpl<LikepostMapper, Likepost> implements ILikepostService {
    @Autowired
    private LikepostMapper likepostMapper;
    @Autowired
    private PostMapper postMapper;

    @Override
    public void addLikePost(Likepost likepost) {
        int postId=likepost.getPostId();
        QueryWrapper<Post> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("postId", postId);
        Post post=postMapper.selectOne(queryWrapper);
        post.setLikeCount(post.getLikeCount()+1);
        postMapper.updateById(post);
        likepostMapper.insert(likepost);
    }

    @Override
    public void deleteLikePost(int postId,int userId) {
        QueryWrapper<Likepost> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("postId", postId).eq("userId", userId);

        QueryWrapper<Post> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.eq("postId",postId);
        Post post = postMapper.selectOne(queryWrapper1);
        post.setLikeCount(post.getLikeCount()-1);
        likepostMapper.delete(queryWrapper);
        postMapper.updateById(post);
    }

    @Override
    public List<Likepost> getLikePosts(int userId) {
        QueryWrapper<Likepost> wrapper = new QueryWrapper<>();
        wrapper.eq("userId", userId);
        return likepostMapper.selectList(wrapper);
    }

    @Override
    public String getIfLikePost(int userId, int postId) {
        QueryWrapper<Likepost> likepostQueryWrapper = new QueryWrapper<Likepost>();
        likepostQueryWrapper.eq("userID", userId);
        likepostQueryWrapper.eq("postID", postId);
        Likepost likepost = likepostMapper.selectOne(likepostQueryWrapper);
        if (likepost == null) {
            return "false";
        }
        else{
            return "true";
        }
    }

    @Override
    public boolean isDuplicateLikePost(int userId, int postId) {
        QueryWrapper<Likepost> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("postId", postId).eq("userId", userId);
        Likepost existingLikepost = likepostMapper.selectOne(queryWrapper);
        return existingLikepost != null;
    }

    @Override
    public List<Post> getLikePostByUserId(int userId) {
        QueryWrapper<Likepost> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userId", userId);
        List<Likepost>likeposts=likepostMapper.selectList(queryWrapper);
        List<Post>posts= new ArrayList<>();
        for (int i = 0; i < likeposts.size(); i++) {
            QueryWrapper<Post> queryWrapper1 = new QueryWrapper<>();
            queryWrapper1.eq("postId", likeposts.get(i).getPostId());
            Post post=postMapper.selectOne(queryWrapper1);
            System.out.println(post.getPostId());
            posts.add(post);
        }
        return posts;
    }
}
