package com.example.habitbuilder.service;

import com.example.habitbuilder.pojo.Collectpost;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 实训小组
 * @since 2024-07-06
 */
public interface ICollectpostService extends IService<Collectpost> {

    void addCollection(Collectpost collectpost);

    void deleteCollection(int postId,int userId);

    String getPostCollections(int userId, int postId);

    Object getCollections(int userId);

    boolean isDuplicateCollection(int postId, int userId);
}
