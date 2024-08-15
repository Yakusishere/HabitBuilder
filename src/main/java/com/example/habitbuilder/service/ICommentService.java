package com.example.habitbuilder.service;

import com.example.habitbuilder.domain.vo.CommentVo;
import com.example.habitbuilder.pojo.Comment;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 实训小组
 * @since 2024-07-03
 */
public interface ICommentService extends IService<Comment> {

    List<CommentVo> getPostCommentSection(int postId);

    List<CommentVo> listByUserId(String token);

    void addComment(Comment comment);

    void deleteComment(int commentId);

    CommentVo ConvertToCommentVo(int userId, Comment comment);
}
