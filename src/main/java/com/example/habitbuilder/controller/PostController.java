package com.example.habitbuilder.controller;

import com.example.habitbuilder.pojo.Post;
import com.example.habitbuilder.pojo.Result;
import com.example.habitbuilder.service.IPostService;
import com.example.habitbuilder.utils.AliOSSUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

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
    IPostService postService;

    @Autowired
    private AliOSSUtils aliOSSUtils;

    /**
     * 获取所有帖子
     *
     * @return {@link List }<{@link Post }>
     */
    @GetMapping("/list")
    public Result getAllPosts(){
        return Result.success(postService.getAllPost(),"获取所有帖子成功");
    }

    /**
     * 删除帖子
     *
     * @param postId 帖子id
     * @return {@link Result }
     */
    @DeleteMapping("/deletePost")
    public Result deletePost(@RequestParam int postId) {
        postService.deletePost(postId);
        return Result.success("删除帖子成功");
    }

    @PostMapping("/upload")
    public Result upload(MultipartFile image) throws IOException {
        String url = aliOSSUtils.upload(image);
        return Result.success(url, "上传成功");
    }
    @PostMapping("/createPost")
    public Result createPost(@RequestBody Post post) {
        post.setPublishDate(LocalDate.now());
        postService.addPost(post);
        return Result.success("创建帖子成功");
    }

    @PutMapping("/updatePost")
    public Result updatePost(@RequestBody Post post) {
        postService.updatePost(post);
        return Result.success("更新帖子成功");
    }

    @GetMapping("/searchPost")
    public Result searchPost(String title){
        List<Post>posts=postService.searchPost(title);
        if(posts.isEmpty()){
            return Result.error("搜索无结果");
        }else {
            return Result.success(posts,"搜索成功");
        }
    }

    @GetMapping("/getPostByUserId")
    public Result getPostByUserId(int userId){
        List<Post>posts=postService.getPostByUserId(userId);
        if(posts.isEmpty()){
            return Result.error("该用户未发布帖子");
        }else{
            return Result.success(posts,"查找成功");
        }
    }
}
