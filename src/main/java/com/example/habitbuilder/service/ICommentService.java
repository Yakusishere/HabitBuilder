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

    void addComment(Comment comment);

    void addReplyComment(Comment comment);

    List<Comment[]> getThisPostComments(int postId);

    void deleteComment(int commentId);

    CommentVo ConvertToCommentVo(int userId, Comment comment);
}
