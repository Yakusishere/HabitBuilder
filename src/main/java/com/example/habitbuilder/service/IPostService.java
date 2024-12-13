package com.example.habitbuilder.service;

import com.example.habitbuilder.domain.PageQuery;
import com.example.habitbuilder.domain.vo.PostOverviewVo;
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

    List<PostOverviewVo> getPostList(Post post, PageQuery pageQuery);

    List<PostOverviewVo> getFavPostList(String token);

    List<PostOverviewVo> getLikePostList(String token);

    PostVo browsePost(String token, int postId);

    void addPost(String token, Post post);

    void deletePost(int postId);

    void updatePost(String token, Post post);

    List<Post> searchPost(String title);

    List<Post> getPostByUserId(int userId);
}
