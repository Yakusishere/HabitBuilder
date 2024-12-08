//package com.example.habitbuilder.config;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Bean;
//import com.example.habitbuilder.utils.JwtUtils;
//import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
//@Configuration
//public class MyWebMvcConfig implements WebMvcConfigurer {
//    @Autowired
//    private JwtUtils jwtUtils;
//
//    @Bean
//    public TokenInterceptor tokenInterceptor() {
//        return new TokenInterceptor(jwtUtils);
//    }
//
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(tokenInterceptor())
//                .addPathPatterns("/**")
//                .excludePathPatterns("/user/login", "/user/register");
//    }
//}
