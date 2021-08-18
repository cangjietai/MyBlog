package com.lst.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @Author: Solitude
 * @Data: 2021/8/17 05:03
 * @Description:
 */
@Controller
public class IndexController {

    @GetMapping("/")
    public String index() {

        return "index";
   }
}
