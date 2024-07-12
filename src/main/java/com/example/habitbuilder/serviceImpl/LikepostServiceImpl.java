package com.example.habitbuilder.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.habitbuilder.pojo.Likepost;
import com.example.habitbuilder.mapper.LikepostMapper;
import com.example.habitbuilder.service.ILikepostService;
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
 * @since 2024-07-06
 */
@Service
public class LikepostServiceImpl extends ServiceImpl<LikepostMapper, Likepost> implements ILikepostService {
    @Autowired
    private LikepostMapper likepostMapper;

    @Override
    public void addLikePost(Likepost likepost) {
        likepostMapper.insert(likepost);
    }

    @Override
    public void deleteLikePost(int postId,int userId) {
        QueryWrapper<Likepost> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("postId", postId).eq("userId", userId);
        likepostMapper.delete(queryWrapper);
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
}
