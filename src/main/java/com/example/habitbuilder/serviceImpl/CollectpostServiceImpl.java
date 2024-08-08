package com.example.habitbuilder.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.habitbuilder.mapper.PostMapper;
import com.example.habitbuilder.pojo.Collectpost;
import com.example.habitbuilder.mapper.CollectpostMapper;
import com.example.habitbuilder.pojo.Likepost;
import com.example.habitbuilder.pojo.Post;
import com.example.habitbuilder.service.ICollectpostService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

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
public class CollectpostServiceImpl extends ServiceImpl<CollectpostMapper, Collectpost> implements ICollectpostService {
    @Autowired
    private CollectpostMapper collectpostMapper;
    @Autowired
    private PostMapper postMapper;

    @Override
    public void addCollection(Collectpost collectpost) {
        int postId=collectpost.getPostId();
        QueryWrapper<Post> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("postId", postId);
        Post post=postMapper.selectOne(queryWrapper);
        post.setFavCount(post.getFavCount()+1);
        postMapper.updateById(post);
        collectpostMapper.insert(collectpost);
    }

    @Override
    public void deleteCollection(int postId,int userId) {
        QueryWrapper<Collectpost> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("postId", postId).eq("userId", userId);
        QueryWrapper<Post> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.eq("postId",postId);
        Post post = postMapper.selectOne(queryWrapper1);
        post.setFavCount(post.getFavCount()-1);
        collectpostMapper.delete(queryWrapper);
        postMapper.updateById(post);
    }

    @Override
    public List<Post> getCollections(int userId) {
        QueryWrapper<Collectpost> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userId", userId);
        List<Collectpost>collectposts=collectpostMapper.selectList(queryWrapper);
        List<Post>posts= new ArrayList<>();
        for (int i = 0; i < collectposts.size(); i++) {
            QueryWrapper<Post> queryWrapper1 = new QueryWrapper<>();
            queryWrapper1.eq("postId", collectposts.get(i).getPostId());
            Post post=postMapper.selectOne(queryWrapper1);
            posts.add(post);
        }
        return posts;
    }

    @Override // 找一个post是否为收藏
    public String getPostCollections(int userId, int postId) {
        QueryWrapper<Collectpost> queryWrapper = new QueryWrapper<Collectpost>();
        queryWrapper.eq("userId", userId);
        queryWrapper.eq("postId", postId);
        if(!collectpostMapper.selectList(queryWrapper).isEmpty()){ // 说明用户对帖子是否关注
            return "true";
        }
        else {
            return "false";
        }
    }

    @Override
    public boolean isDuplicateCollection(int userId, int postId) {
        QueryWrapper<Collectpost> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("postId", postId).eq("userId", userId);
        Collectpost existingCollection = collectpostMapper.selectOne(queryWrapper);
        return existingCollection != null;
    }
}
