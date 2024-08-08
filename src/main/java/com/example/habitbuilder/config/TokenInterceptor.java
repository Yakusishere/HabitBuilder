package com.example.habitbuilder.config;

import com.example.habitbuilder.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServletResponseWrapper;

public class TokenInterceptor implements HandlerInterceptor {
	private final JwtUtils jwtUtils;

	public TokenInterceptor(JwtUtils jwtUtils) {
		this.jwtUtils = jwtUtils;
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		System.out.println("调用了方法");
		HttpServletResponseWrapper wrapper = new HttpServletResponseWrapper(response);
		String token = request.getHeader("Authorization");
		System.out.println(token);
		if (jwtUtils.verify(token) != 0) {
			wrapper.sendRedirect("fail/token/invalid");
			System.out.println("invalid token");
			return false;
		}
		System.out.println("valid token");
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
		System.out.println("拦截器处理结束...");
		HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
		System.out.println("请求结束...");
		HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
	}
}
