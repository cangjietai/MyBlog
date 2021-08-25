package com.lst.web;

import com.lst.pojo.Type;
import com.lst.service.BlogService;
import com.lst.service.TypeService;
import com.lst.vo.BlogQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * @Author: Solitude
 * @Data: 2021/8/24 20:37
 * @Description:
 */
@Controller
public class TypeShowController {

    @Autowired
    private TypeService typeService;
    @Autowired
    private BlogService blogService;

    //当前选中的type的id
    @GetMapping("/types/{id}")
    public String types(@PageableDefault(size=8,sort={"updateTime"},direction= Sort.Direction.DESC)
                        Pageable pageable, @PathVariable Long id, Model model){
        //设置查询前10000，足够查询到所有的分页数据了
        List<Type> types =typeService.listTypeTop(10000);
        //第一次进入该页面，还没有选择按类别分类，这里的id是type的id
        //在index页面设置id的初始值是-1，id==-1说明是从首页点进来的
        if(id==-1){
            id=types.get(0).getId();
        }
        BlogQuery blogQuery=new BlogQuery();
        blogQuery.setTypeId(id);
        model.addAttribute("types",types);
        model.addAttribute("page",blogService.listBlog(pageable,blogQuery));
        model.addAttribute("activeTypeId",id);//选中的颜色区分变亮
        return "types";
    }
}
