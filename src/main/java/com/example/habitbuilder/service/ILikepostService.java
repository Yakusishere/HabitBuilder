package com.example.habitbuilder.service;

import com.example.habitbuilder.pojo.Likepost;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.habitbuilder.pojo.Post;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 实训小组
 * @since 2024-07-06
 */
public interface ILikepostService extends IService<Likepost> {
    Boolean likePost(String token, int postId);

    Boolean deleteLikePost(String token, int postId);

    List<Likepost> getLikePosts(int userId);

    String getIfLikePost(int userId, int postId);

    List<Post> getLikePostByUserId(int userId);
}
