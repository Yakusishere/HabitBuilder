package com.example.habitbuilder.service;

import com.example.habitbuilder.domain.PageQuery;
import com.example.habitbuilder.domain.vo.UserVo;
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
    List<UserVo> getFollowList(String token, PageQuery pageQuery);

    List<UserVo> getFanList(String token, PageQuery pageQuery);

    void addFollowUser(Followuser followuser);

    void deleteFollowUser(int followUserId);

    List<Followuser> getFollowUsers(int receiveUserId);

    String getIfFollow(int sendUserId ,int receiveUserId);
}
