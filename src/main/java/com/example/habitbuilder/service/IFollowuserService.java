package com.example.habitbuilder.service;

import com.example.habitbuilder.pojo.Followuser;
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
public interface IFollowuserService extends IService<Followuser> {
    void addFollowUser(Followuser followuser);

    void deleteFollowUser(int followUserId);

    List<Followuser> getFollowUsers(int sendUserId);

    String getIfFollow(int sendUserId ,int receiveUserId);
}
