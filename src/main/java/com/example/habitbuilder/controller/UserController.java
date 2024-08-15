package com.example.habitbuilder.controller;

import com.example.habitbuilder.domain.PageQuery;
import com.example.habitbuilder.domain.bo.UserBo;
import com.example.habitbuilder.mapper.*;
import com.example.habitbuilder.pojo.Result;
import com.example.habitbuilder.pojo.User;
import com.example.habitbuilder.service.IUserService;
import com.example.habitbuilder.serviceImpl.UserServiceImpl;
import com.example.habitbuilder.utils.LoginHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 前端控制器
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

	@Autowired
	private LoginHelper loginHelper;

	/**
	 * 获取用户列表
	 *
	 * @param user      用户
	 * @param pageQuery 页面查询
	 * @return {@link Result }
	 */
	@PostMapping("/list")
	public Result getUserList(@RequestBody User user, PageQuery pageQuery) {
		return Result.success(userService.getUserList(user, pageQuery), "查询成功");
	}

	/**
	 * 按用户id获取
	 *
	 * @param userId 用户id
	 * @return {@link Result }
	 */
	@GetMapping("/getByUserId")
	public Result getByUserId(@RequestParam int userId) {
		User user = userService.getByUserId(userId);
		if (user == null) {
			return Result.error("该用户不存在");
		}
		return Result.success(user, "查询成功");
	}

	/**
	 * 更新用户
	 *
	 * @param user 用户
	 * @return {@link Result }
	 */
	@PutMapping("/update")
	public Result updateUser(@RequestBody User user) {
		boolean flag = userService.updateById(user);
		if (!flag) {
			return Result.error("更新失败");
		}
		return Result.success("更新成功");
	}


	/**
	 * 删除用户
	 *
	 * @param id 身份证件
	 * @return {@link Result }
	 */
	@DeleteMapping("/delete")
	public Result deleteUser(Integer id) {
		boolean flag = userService.deleteById(id);
		if (!flag) {
			return Result.error("删除失败");
		}
		return Result.success("删除成功");
	}

	/**
	 * 登录
	 *
	 * @param userRequest 用户请求
	 * @return {@link Result }
	 */
	@PostMapping("/login")
	public Result login(@RequestBody User userRequest) {
		String username = userRequest.getUserName();
		String password = userRequest.getPassword();
		String token = userService.login(username, password);
		if (token == null) {
			return Result.error("用户名或密码错误");
		}
		return Result.success(token, "登陆成功");
	}

	/**
	 * 登记
	 *
	 * @param userRequest 用户请求
	 * @return {@link Result }
	 */
	@PostMapping("/register")
	public Result register(@RequestBody User userRequest) {
		String username = userRequest.getUserName();
		String password = userRequest.getPassword();
		boolean flag = userService.findByUserName(username);
		if (!flag) {
			userService.register(username, password);
			return Result.success("注册成功");
		} else {
			return Result.error("用户名已存在");
		}
	}
}
