package com.example.habitbuilder.controller;

import com.example.habitbuilder.pojo.Post;
import com.example.habitbuilder.pojo.Result;
import com.example.habitbuilder.serviceImpl.PostServiceImpl;
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
@RequestMapping("/post")
public class PostController {
    @Autowired
    PostServiceImpl postService;

    @PostMapping("/createPost")
    public Result createPost(@RequestBody Post post) {
        post.setPublishDate(LocalDate.now());
        postService.addPost(post);
        return Result.success("创建帖子成功");
    }

    @DeleteMapping("/deletePost")
    public Result deletePost(int postId) {
        postService.deletePost(postId);
        return Result.success("删除帖子成功");
    }

    @PutMapping("/updatePost")
    public Result updatePost(@RequestBody Post post) {
        postService.updatePost(post);
        return Result.success("更新帖子成功");
    }

    @GetMapping("/getAllPost")
    public Result getAllPost() {
        return Result.success(postService.getAllPost(),"获取所有帖子成功");
    }

}
