package com.example.habitbuilder.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/post/upload") // 允许跨域的路径，可以修改为你想允许的路径
                .allowCredentials(true) // 是否允许携带 Cookie
                .allowedOrigins("http://localhost:5173") // 允许所有源进行跨域请求，可以改为特定域名，比如 "http://localhost:3000"
                .allowedMethods("GET", "POST", "PUT", "DELETE") // 允许的请求方法
                .allowedHeaders("*") // 允许的请求头，设置为 "*" 代表允许所有请求头
                .exposedHeaders("*"); // 允许暴露的响应头，设置为 "*" 代表暴露所有响应头
    }
}
