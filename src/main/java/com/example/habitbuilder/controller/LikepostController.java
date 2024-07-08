package com.example.habitbuilder.controller;

import com.example.habitbuilder.pojo.Likepost;
import com.example.habitbuilder.pojo.Result;
import com.example.habitbuilder.serviceImpl.LikepostServiceImpl;
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
@RequestMapping("/community")
public class LikepostController {
    @Autowired
    LikepostServiceImpl likepostService;

    @PostMapping("/addLikePost")
    public Result addLikePost(@RequestBody Likepost likepost) {
        likepostService.addLikePost(likepost);
        return Result.success("点赞帖子成功");
    }

    @DeleteMapping("/deleteLikePost")
    public Result deleteLikePost(int LikePostId) {
        likepostService.deleteLikePost(LikePostId);
        return Result.success("取消帖子点赞成功");
    }

    @GetMapping("/getLikePosts") //调用展示时，直接找这个数据进行判断就行了
    public Result getLikePosts(int userId) {

        return Result.success(likepostService.getLikePosts(userId),"获取喜欢列表成功");

    }

    @PostMapping("/getIfLikePost") //感觉这个不需要
    public Result getIfLikePost(int userId,int postId) {

        return Result.success(likepostService.getIfLikePost(userId,postId));

    }
}
