package com.example.habitbuilder.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.habitbuilder.mapper.CommentMapper;
import com.example.habitbuilder.pojo.Comment;
import com.example.habitbuilder.pojo.Likecomments;
import com.example.habitbuilder.mapper.LikecommentsMapper;
import com.example.habitbuilder.pojo.Likepost;
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

    @Autowired
    private CommentMapper commentMapper;

    @Override
    public void addLikeComment(Likecomments likecomments) {
        int commentId=likecomments.getCommentId();
        QueryWrapper<Comment> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("commentId", commentId);
        Comment comment=commentMapper.selectOne(queryWrapper);
        comment.setLikeCount(comment.getLikeCount()+1);
        commentMapper.updateById(comment);
        likecommentsMapper.insert(likecomments);
    }

    @Override
    public void deleteLikeComment(int commentId,int userId) {
        QueryWrapper<Likecomments> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("commentId", commentId).eq("userId", userId);
        QueryWrapper<Comment> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.eq("commentId",commentId);
        Comment comment = commentMapper.selectOne(queryWrapper1);
        comment.setLikeCount(comment.getLikeCount()-1);
        likecommentsMapper.delete(queryWrapper);
        commentMapper.updateById(comment);
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



    public List<Likecomments> getLikeComment(int userId){
        QueryWrapper<Likecomments> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userId", userId);
        return likecommentsMapper.selectList(queryWrapper);
    }

    @Override
    public boolean isDuplicateLikeComment(int commentId, int userId) {
        QueryWrapper<Likecomments> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("commentId", commentId).eq("userId", userId);
        Likecomments existingLikecomment = likecommentsMapper.selectOne(queryWrapper);
        return existingLikecomment != null;
    }
}
