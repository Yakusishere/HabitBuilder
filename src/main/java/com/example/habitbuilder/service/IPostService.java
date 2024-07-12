package com.example.habitbuilder.service;

import com.example.habitbuilder.pojo.Post;
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
public interface IPostService extends IService<Post> {

    void addPost(Post post);

    void deletePost(int postId);

    void updatePost(Post post);

    List<Post> getAllPost();

    List<Post> searchPost(String title);

    List<Post> getPostByUserId(int userId);
}
