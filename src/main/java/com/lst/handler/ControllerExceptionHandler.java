package com.lst.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author: Solitude
 * @Data: 2021/8/17 05:24
 * @Description:
 * 统一处理异常
 */
//会拦截全部带有Controller注解的控制器
@ControllerAdvice
public class ControllerExceptionHandler {

    private final Logger logger= LoggerFactory.getLogger(this.getClass());


    //@Exception注解标识这个注解可以做异常处理，可以拦截Exception级别的异常信息
    @ExceptionHandler(Exception.class)
    public ModelAndView exceptionHandler(HttpServletRequest request, Exception e) throws Exception {
        logger.error("Request URL: {}, Exception : {}", request.getRequestURL(), e);

        //判断异常是否标识了ResponseStatus状态码,有的话就不要对其进行拦截处理，直接抛出异常
        //标识了状态码，让它交给SpringBoot进行处理，它就会根据状态码找到对应的页面进行返回
        if(AnnotationUtils.findAnnotation(e.getClass(), ResponseStatus.class) !=null){
            throw e;
        }

        ModelAndView mv=new ModelAndView();
        mv.addObject("url",request.getRequestURL());
        mv.addObject("exception",e);
        mv.setViewName("error/error");
        return mv;
    }




}
