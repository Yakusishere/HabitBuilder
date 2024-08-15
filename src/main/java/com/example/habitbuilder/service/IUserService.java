package com.example.habitbuilder.service;

import com.example.habitbuilder.domain.PageQuery;
import com.example.habitbuilder.domain.bo.UserBo;
import com.example.habitbuilder.pojo.User;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 实训小组
 * @since 2024-07-03
 */
public interface IUserService extends IService<User> {

    List<User> getUserList(User user, PageQuery pageQuery);

    User getByUserId(int userId);

    User findByUserId(int userId);

    //根据用户名查找用户
    boolean findByUserName(String username);

    //注册
    void register(String username, String password);

    String login(String username, String password);

    void changeScore(int userId);

    boolean deleteById(Integer id);


}
