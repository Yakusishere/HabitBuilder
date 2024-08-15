package com.example.habitbuilder.service;

import com.example.habitbuilder.pojo.Likecomments;
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
public interface ILikecommentsService extends IService<Likecomments> {
    void addLikeComment(Likecomments likecomments);

    void deleteLikeComment(int commentId,int userId);

    Boolean getIfLikeComment(int userId, int commentId);

    List<Likecomments> getLikeComment(int userId);

    boolean isDuplicateLikeComment(int commentId, int userId);
}
