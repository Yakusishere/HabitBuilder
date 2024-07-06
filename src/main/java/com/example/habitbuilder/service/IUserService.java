package com.example.habitbuilder.service;

import com.example.habitbuilder.pojo.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 实训小组
 * @since 2024-07-03
 */
public interface IUserService extends IService<User> {

    //根据用户名查找用户

    boolean findByUserName(String username);

    //注册
    void register(String username, String password);

    User login(String username, String password);

    void changeScore(int userId);

    boolean deleteById(Integer id);

    User getUserId(Integer id);
}
