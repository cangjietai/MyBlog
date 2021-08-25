package com.lst.web;

import com.lst.service.BlogService;
import com.lst.service.TagService;
import com.lst.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Author: Solitude
 * @Data: 2021/8/17 05:03
 * @Description:
 */
@Controller
public class IndexController {

    @Autowired
    private BlogService blogService;

    @Autowired
    private TypeService typeService;

    @Autowired
    private TagService tagService;

    @GetMapping("/")
    public String index(@PageableDefault(size=8,sort={"updateTime"},direction= Sort.Direction.DESC)
                        Pageable pageable, Model model) {

        //显示分页博客
        model.addAttribute("page",blogService.listBlog(pageable));
        //显示前6个type
        model.addAttribute("types",typeService.listTypeTop(6));
        //显示前10个tag
        model.addAttribute("tags",tagService.listTagTop(10));
        //显示前8条推荐博客recommendBlogs
        model.addAttribute("recommendBlogs",blogService.listReCommendBlogTop(8));
        return "index";
   }


   //首页查询
   @PostMapping("/search")
   public String search(@PageableDefault(size=8,sort={"updateTime"},direction=Sort.Direction.DESC)
                        Pageable pageable,
                        @RequestParam String query, Model model){

        model.addAttribute("page",blogService.listBlog("%"+query+"%",pageable));
        model.addAttribute("query",query);
        return "search";
   }

   @GetMapping("/blog/{id}")
   public String blog(@PathVariable Long id,Model model){
        model.addAttribute("blog",blogService.getAndConvert(id));
        return "blog";
   }


    @GetMapping("/footer/newblog")
   public String newblogs(Model model){
        model.addAttribute("newblogs",blogService.listReCommendBlogTop(3));
        return "_fragments::newblogList";
   }

}
