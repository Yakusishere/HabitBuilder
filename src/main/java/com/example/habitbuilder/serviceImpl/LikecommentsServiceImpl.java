package com.example.habitbuilder.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.habitbuilder.pojo.Likecomments;
import com.example.habitbuilder.mapper.LikecommentsMapper;
import com.example.habitbuilder.service.ILikecommentsService;
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
public class LikecommentsServiceImpl extends ServiceImpl<LikecommentsMapper, Likecomments> implements ILikecommentsService {
    @Autowired
    private LikecommentsMapper likecommentsMapper;

    @Override
    public void addLikeComment(Likecomments likecomments) {
        likecommentsMapper.insert(likecomments);
    }

    @Override
    public void deleteLikeComment(int likeCommentId) {
        likecommentsMapper.deleteById(likeCommentId);
    }

    public String getIfLikeComment(int userId, int commentId) {
        QueryWrapper<Likecomments> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userId", userId);
        queryWrapper.eq("commentId", commentId);
        if(!likecommentsMapper.selectList(queryWrapper).isEmpty()){
            return "true";
        }else{
            return "false";
        }
    }



    public List<Likecomments> getLikeComment(int userId,int postId){
        QueryWrapper<Likecomments> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userId", userId);
        queryWrapper.eq("postId", postId); // 查到这个用户对这个帖子的所有点赞关系
        return likecommentsMapper.selectList(queryWrapper);
    }
}
