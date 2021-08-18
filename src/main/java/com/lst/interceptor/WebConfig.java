package com.lst.interceptor;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @Author: Solitude
 * @Data: 2021/8/18 15:17
 * @Description:
 * 重写关于控制器controller的设置
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor())
        .addPathPatterns("/admin/**")    //拦截admin下的所有请求
        .excludePathPatterns("/admin")   //放行/admin和/admin/login页面
        .excludePathPatterns("/admin/login");
    }
}
