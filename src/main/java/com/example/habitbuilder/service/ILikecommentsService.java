package com.example.habitbuilder.service;

import com.example.habitbuilder.pojo.Likecomments;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author 实训小组
 * @since 2024-07-06
 */
public interface ILikecommentsService extends IService<Likecomments> {
	Boolean addLikeComment(String token, int commentId);

	Boolean deleteLikeComment(String token, int commentId);

	Boolean getIfLikeComment(int userId, int commentId);

	List<Likecomments> getLikeComment(String token);
}
