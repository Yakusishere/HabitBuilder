package com.example.habitbuilder.controller;

import com.example.habitbuilder.pojo.Result;
import com.example.habitbuilder.pojo.User;
import com.example.habitbuilder.service.IUserService;
import com.example.habitbuilder.serviceImpl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    // 删除用户
    @DeleteMapping("/delete")
    public Result deleteUser(Integer id) {
        boolean flag=userService.deleteById(id);
        if(!flag) {
            return Result.error("删除失败");
        }
        return Result.success("删除成功");
    }

    // 更新用户
    @PutMapping("/update")
    public Result updateUser(@RequestBody User user) {
        boolean flag = userService.updateById(user);
        if(!flag) {
            return Result.error("更新失败");
        }
        return Result.success("更新成功");
    }

    // 查询所有用户
    @GetMapping("/getAll")
    public Result getAllUsers() {
        List<User> users =userService.list();
        if(users.isEmpty()){
            return Result.error("无用户");
        }
        return Result.success(users,"查询成功");
    }

    // 根据 ID 查询用户
    @GetMapping("/getId")
    public Result getUserById(Integer id) {
        User user = userService.getUserId(id);
        if(user==null){
            return Result.error("该用户不存在");
        }
        return Result.success("查询成功");
    }
    @PostMapping("/register")
    public Result register(@RequestBody User userRequest){
        String username=userRequest.getUserName();
        String password=userRequest.getPassword();
        boolean flag=userService.findByUserName(username);
        if(!flag){
            userService.register(username,password);
            return Result.success("注册成功");
        }else{
            return Result.error("用户名已存在");
        }
    }
    @PostMapping("/login")
    public Result login(@RequestBody User userRequest){
        String username=userRequest.getUserName();
        String password=userRequest.getPassword();
        User user=userService.login(username,password);
        if(user==null){
            return Result.error("用户名或密码错误");
        }
        return Result.success(user,"登陆成功");
    }
}
