package com.example.habitbuilder.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.habitbuilder.pojo.User;
import com.example.habitbuilder.mapper.UserMapper;
import com.example.habitbuilder.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 实训小组
 * @since 2024-07-03
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {
    @Autowired
    private UserMapper userMapper;
    @Override
    public boolean findByUserName(String username) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userName", username);
        List<User> users=userMapper.selectList(queryWrapper);
        if(users.isEmpty()){
            return false;
        }
        return true;
    }

    @Override
    public void register(String username, String password) {
        LocalDateTime localDateTime= LocalDateTime.now();
        //添加
        userMapper.insert(new User(null,username,password,null,localDateTime,0));
    }

    @Override
    public boolean login(String username, String password) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.allEq(Map.of( "userName", username, "password", password));
        List<User>users=userMapper.selectList(queryWrapper);
        if(users.isEmpty()){
            return false;
        }
        return true;
    }
}
