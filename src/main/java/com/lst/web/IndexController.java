package com.lst.web;

import com.lst.NotFoundException;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @Author: Solitude
 * @Data: 2021/8/17 05:03
 * @Description:
 */
@Controller
public class IndexController {

    @GetMapping("/{id}/{name}")
    public String index(@PathVariable Integer id, @PathVariable  String name) {

//        int i=1/0;
 /*       String blog=null;
        if ((blog==null)) {

            throw new NotFoundException("博客不存在");
        }
        return "index";
    }*/
        System.out.println("-------index-------");
        return "index";
   }
}
