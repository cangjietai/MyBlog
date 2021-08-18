package com.lst.web.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Author: Solitude
 * @Data: 2021/8/18 14:55
 * @Description:
 */
@Controller
@RequestMapping("/admin")
public class BlogController {

    //返回blogs后台管理页面
    @GetMapping("/blogs")
    public String blogs(){
        return "admin/blogs";
    }
}
