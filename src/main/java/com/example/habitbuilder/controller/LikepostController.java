package com.example.habitbuilder.controller;

import com.example.habitbuilder.pojo.Likepost;
import com.example.habitbuilder.pojo.Post;
import com.example.habitbuilder.pojo.Result;
import com.example.habitbuilder.serviceImpl.LikepostServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 实训小组
 * @since 2024-07-06
 */
@RestController
@RequestMapping("/likepost")
public class LikepostController {
    @Autowired
    LikepostServiceImpl likepostService;

    @PostMapping("/addLikePost")
    public Result addLikePost(@RequestBody Likepost likepost) {
        boolean isDuplicate = likepostService.isDuplicateLikePost(likepost.getPostId(), likepost.getUserId());
        if (isDuplicate) {
            return Result.error("重复点赞");
        }
        likepostService.addLikePost(likepost);
        return Result.success("点赞帖子成功");
    }

    @DeleteMapping("/deleteLikePost")
    public Result deleteLikePost(int postId,int userId) {
        likepostService.deleteLikePost(postId,userId);
        return Result.success("取消帖子点赞成功");
    }

    @GetMapping("/getLikePosts")
    public Result getLikePosts(int userId) {

        return Result.success(likepostService.getLikePosts(userId),"获取喜欢列表成功");

    }

    @PostMapping("/getIfLikePost")
    public Result getIfLikePost(int userId,int postId) {
        return Result.success(likepostService.getIfLikePost(userId,postId));
    }

    @GetMapping("/getLikePostByUserId")
    public Result getLikePostByUserId(int userId){
        List<Post>posts=likepostService.getLikePostByUserId(userId);
        if(posts.isEmpty()){
            return Result.error("无点赞过的帖子");
        }else {
            return Result.success(posts,"获取成功");
        }
    }
}
