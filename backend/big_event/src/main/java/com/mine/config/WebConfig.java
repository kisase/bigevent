package com.mine.config;

import com.mine.interceptors.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {


    private final LoginInterceptor loginInterceptor;

    public WebConfig(LoginInterceptor loginInterceptor) {
        this.loginInterceptor = loginInterceptor;
    }
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //登录接口和注册不拦截，其他拦截
        registry.addInterceptor(loginInterceptor).excludePathPatterns("/user/login","/user/register","/uploads/**");
    }



}
