package com.example.habitbuilder.mapper;

import com.example.habitbuilder.pojo.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import lombok.Data;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 实训小组
 * @since 2024-07-03
 */

public interface UserMapper extends BaseMapper<User> {

    //根据用户名查找用户
    User findByUserName(String username);

    //添加
    void add(String username, String password);
}
