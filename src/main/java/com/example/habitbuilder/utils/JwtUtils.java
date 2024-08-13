package com.example.habitbuilder.utils;

import com.example.habitbuilder.pojo.User;
import com.example.habitbuilder.service.IUserService;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtils {
	@Autowired
	private IUserService userService;
	/**
	 * 过期时间5分钟
	 */
	private static final long EXPIRE_TIME = 5 * 60 * 1000;
	/**
	 * 加密密钥
	 */
	private static final String KEY = "liuhongfei";

	/**
	 * 生成token
	 *
	 * @param id       用户id
	 * @param userName 用户名
	 * @return
	 */
	public String createToken(int id, String userName) {
		Map<String, Object> header = new HashMap();
		header.put("typ", "JWT");
		header.put("alg", "HS256");
		//setID:用户ID
		//setExpiration:token过期时间  当前时间+有效时间
		//setSubject:用户名
		//setIssuedAt:token创建时间
		//signWith:加密方式
		JwtBuilder builder = Jwts.builder().setHeader(header)
				.setId(String.valueOf(id))
				.setExpiration(new Date(System.currentTimeMillis() + EXPIRE_TIME))
				.setSubject(userName)
				.setIssuedAt(new Date())
				.signWith(SignatureAlgorithm.HS256, KEY);
		return builder.compact();
	}

	/**
	 * 验证token是否有效
	 *
	 * @param token 请求头中携带的token
	 * @return token验证结果  2-token过期；1-token认证失败；0-token认证通过
	 */
	public int verify(String token) {
		Claims claims = null;
		try {
			//token过期后，会抛出ExpiredJwtException 异常，通过这个来判定token过期，
			claims = Jwts.parser().setSigningKey(KEY).parseClaimsJws(token).getBody();
		} catch (ExpiredJwtException e) {
			return 2;
		}
		//从token中获取用户id，查询该Id的用户是否存在，存在则token验证通过
		String id = claims.getId();
		User user = userService.findByUserId(Integer.parseInt(id));
		if (user == null) {
			return 1;
		} else {
			return 0;
		}
	}

	public int extractUserId(String token){
		Claims claims = null;
		try {
			//token过期后，会抛出ExpiredJwtException 异常，通过这个来判定token过期，
			claims = Jwts.parser().setSigningKey(KEY).parseClaimsJws(token).getBody();
		} catch (ExpiredJwtException e) {
			return 2;
		}
		//从token中获取用户id，查询该Id的用户是否存在，存在则token验证通过
		return Integer.parseInt(claims.getId());
	}
}
