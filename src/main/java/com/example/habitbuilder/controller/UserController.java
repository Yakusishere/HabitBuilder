package com.example.habitbuilder.controller;

import com.example.habitbuilder.pojo.Result;
import com.example.habitbuilder.pojo.User;
import com.example.habitbuilder.service.IUserService;
import org.apache.ibatis.jdbc.Null;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 实训小组
 * @since 2024-07-03
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private IUserService userService;
    @PostMapping("/register")
    public Result register(String username, String password){
        boolean flag=userService.findByUserName(username);
        if(!flag){
            userService.register(username,password);
            return Result.success("注册成功");
        }else{
            return Result.error("用户名已存在");
        }
    }
    @PostMapping("/login")
    public Result login(String username,String password){
        boolean flag=userService.login(username,password);
        if(!flag){
            return Result.error("用户名或密码错误");
        }
        return Result.success("登陆成功");
    }
}
