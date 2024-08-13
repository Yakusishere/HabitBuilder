package com.example.habitbuilder.utils;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.habitbuilder.mapper.UserMapper;
import com.example.habitbuilder.pojo.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LoginHelper {
	@Autowired
	JwtUtils jwtUtils;

	@Autowired
	UserMapper userMapper;


	/**
	 * 获取用户id
	 *
	 * @param token 令牌
	 * @return int
	 */
	public int getUserId(String token) {
		return jwtUtils.extractUserId(token);
	}


	/**
	 * 获取用户
	 *
	 * @param token 令牌
	 * @return {@link User }
	 */
	public User getUser(String token){
		int userId = getUserId(token);
		return userMapper.selectOne(new LambdaQueryWrapper<User>().eq(User::getUserId,userId));
	}
}
