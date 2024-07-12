package com.example.habitbuilder.service;

import com.example.habitbuilder.pojo.Likepost;
import com.baomidou.mybatisplus.extension.service.IService;

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
    void addLikePost(Likepost likepost);

    void deleteLikePost(int postId,int userId);

    List<Likepost> getLikePosts(int userId);

    String getIfLikePost(int userId, int postId);

    boolean isDuplicateLikePost(int postId, int userId);
}
