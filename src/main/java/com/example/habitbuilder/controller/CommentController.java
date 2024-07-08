package com.example.habitbuilder.controller;

import com.example.habitbuilder.pojo.Comment;
import com.example.habitbuilder.pojo.Post;
import com.example.habitbuilder.pojo.Result;
import com.example.habitbuilder.serviceImpl.CommentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 实训小组
 * @since 2024-07-03
 */
@RestController
@RequestMapping("/community")
public class CommentController {
    @Autowired
    CommentServiceImpl commentService;

    @PostMapping("/replyComment")
    public Result replyComment(@RequestBody Comment comment) {
        comment.setCommentDate(LocalDate.now());
        commentService.addReplyComment(comment);
        return Result.success("回复成功"); //这里的commentCount 可以从前面获得
    }

    @PostMapping("/commentPost")
    public Result commentPost(@RequestBody Comment comment) {
        comment.setCommentDate(LocalDate.now());
        commentService.addComment(comment);
        return Result.success("评论成功"); // 这里的commentCount先获取表中这个值的最大值
    }

    @DeleteMapping("/deleteComment")
    public Result deleteComment(int commentId) {
        commentService.deleteComment(commentId);
        return Result.success("成功删除评论");
    }

    //查看这个帖子的所有评论
    @GetMapping("/getThisPostComments")
    public Result getThisPostComments(int postId) {
        return Result.success(commentService.getThisPostComments(postId),"获取该帖子评论列表成功");
    }



}
