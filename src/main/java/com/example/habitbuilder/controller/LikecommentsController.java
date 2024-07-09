package com.example.habitbuilder.controller;

import com.example.habitbuilder.pojo.Likecomments;
import com.example.habitbuilder.pojo.Result;
import com.example.habitbuilder.serviceImpl.LikecommentsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 实训小组
 * @since 2024-07-06
 */
@RestController
@RequestMapping("/likecomments")
public class LikecommentsController {
    @Autowired
    LikecommentsServiceImpl likeCommentsService;

    @PostMapping("/addLikeComment")
    public Result addLikeComment(@RequestBody Likecomments likecomments) {
        likeCommentsService.addLikeComment(likecomments);
        return Result.success("评论点赞成功");
    }

    @DeleteMapping("/deleteLikeComment")
    public Result deleteLikeComment(int LikeCommentId) {
        likeCommentsService.deleteLikeComment(LikeCommentId);
        return Result.success("取消评论点赞成功");
    }

    @PostMapping("/getIfLikeComment")
    public Result getIfLikeComment(int userId,int commentId) {
        return Result.success(likeCommentsService.getIfLikeComment(userId,commentId));
    }

    @GetMapping("/getLikeComment")
    public Result getLikeComment(int userId) {
        return Result.success(likeCommentsService.getLikeComment(userId),"获取点赞列表成功");
    }
}
