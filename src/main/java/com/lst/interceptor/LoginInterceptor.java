package com.lst.interceptor;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author: Solitude
 * @Data: 2021/8/18 15:12
 * @Description:
 */

public class LoginInterceptor extends HandlerInterceptorAdapter {


    //在请求未到达目的方法之前进行预处理：拦截
    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {
        //未登陆则重定向到登陆页面
        if (request.getSession().getAttribute("user") == null){
            response.sendRedirect("/admin");
            //不要继续执行了
            return false;
        }
        return true;
    }
}
