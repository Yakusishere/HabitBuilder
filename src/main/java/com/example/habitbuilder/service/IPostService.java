package com.example.habitbuilder.service;

import com.example.habitbuilder.domain.PageQuery;
import com.example.habitbuilder.domain.vo.PostVo;
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

    List<PostVo> getPostList(Post post, PageQuery pageQuery);

    void addPost(Post post);

    void deletePost(int postId);

    void updatePost(Post post);

    List<Post> searchPost(String title);

    List<Post> getPostByUserId(int userId);
}
