package com.example.habitbuilder.utils;

import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.JSONWriter;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.habitbuilder.mapper.ManagerMapper;
import com.example.habitbuilder.mapper.UserMapper;
import com.example.habitbuilder.pojo.Manager;
import com.example.habitbuilder.pojo.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.config.annotation.rsocket.RSocketSecurity;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * JWT工具类
 */
@Component
@RequiredArgsConstructor
public class JwtUtil {

    //设置秘钥明文
    public static final String JWT_KEY = "liuxunhao111";
    private static final Logger log = LoggerFactory.getLogger(JwtUtil.class);

    public static String JWT_BLACK_LIST = "JWT_BLACK_LIST:";

    private final StringRedisTemplate stringRedisTemplate;

    private final ManagerMapper managerMapper;
    private final UserMapper userMapper;

    public boolean invalidateJwt(String headerToken) throws Exception {
        String token = convertToken(headerToken);
        if (token == null) return false;
        Algorithm algorithm = Algorithm.HMAC256(JWT_KEY);
        JWTVerifier jwtVerifier = JWT.require(algorithm).build();
        try {
            DecodedJWT verify = jwtVerifier.verify(token);
            String id = verify.getId();
            return deleteToken(id, verify.getExpiresAt());
        } catch (JWTVerificationException e) {
            return false;
        }
    }

    //判断是不是在黑名单的token
    private boolean isInvalidToken(String uuid) {
        return Boolean.TRUE.equals(stringRedisTemplate.hasKey(JWT_BLACK_LIST + uuid));
    }

    //把token加入黑名单
    private boolean deleteToken(String uuid, Date time) {
        if (isInvalidToken(uuid)) {
            return false;
        }
        Date date = new Date();
        long expire = Math.max(time.getTime() - date.getTime(), 0);
        stringRedisTemplate.opsForValue().set(JWT_BLACK_LIST + uuid, "", expire, TimeUnit.MILLISECONDS);
        return true;
    }

    public String createJwt(LoginManager loginManager) {

        Algorithm algorithm = Algorithm.HMAC256(JWT_KEY);

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("alg", "HS256");
        map.put("typ", "JWT");

        String token = JWT.create()
                .withHeader(map)
                .withJWTId(UUID.randomUUID().toString())
                .withClaim("id", loginManager.getUserId())
                .withClaim("name", loginManager.getUsername())
                .withExpiresAt(expireTime())
                .withIssuedAt(new Date())
                .sign(algorithm);
        // 存储 token 到 Redis

        stringRedisTemplate.opsForValue().set("JWT_TOKEN:" + loginManager.getUsername(), token, expireTime().getTime() - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
        return token;
    }

    public DecodedJWT resolveJwt(String authToken) throws Exception {
        String token = convertToken(authToken);
        if (token == null) {
            return null;
        }
        ;
        if (invalidateJwt(token)) {
            return null;
        }
        Algorithm algorithm = Algorithm.HMAC256(JWT_KEY);
        JWTVerifier jwtVerifier = JWT.require(algorithm).build();
        try {
            DecodedJWT verify = jwtVerifier.verify(token);
            if (isInvalidToken(verify.getId())) {
                return null;
            }

            Date expiresAt = verify.getExpiresAt();
            return new Date().after(expiresAt) ? null : verify;
        } catch (JWTVerificationException e) {
            log.error(e.getMessage());
            return null;
        }
    }

    //请求头中获取token信息
    private String convertToken(String authToken) throws Exception {
        if (!StringUtils.hasText(authToken) || !authToken.startsWith("Bearer ")) {
            return null;
        }
        return authToken.substring(7);
    }

    private Date expireTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR, 7 * 24);
        return calendar.getTime();
    }

    public LoginManager toManager(DecodedJWT jwt) {
        Map<String, Claim> claims = jwt.getClaims();
        Manager manager = managerMapper.selectById(claims.get("id").toString());
        User user = userMapper.selectById(claims.get("id").toString());
        if (manager != null) {
            return new LoginManager(manager);
        }
        return new LoginManager(user);
    }

    public int extractUserId(String token) {
        try {
            DecodedJWT jwt = resolveJwt(token);
            if (jwt == null) {
                throw new IllegalArgumentException("Invalid token");
            }
            Map<String, Claim> claims = jwt.getClaims();
            return Integer.parseInt(claims.get("id").toString());
        } catch (Exception e) {
            // 处理异常，例如打印日志、抛出 RuntimeException 等
            log.error("Error extracting user id", e);
            throw new RuntimeException("Error extracting user id", e);
        }
    }

}


