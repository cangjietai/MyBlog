package com.lst.web.admin;

import com.lst.pojo.Type;
import com.lst.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

/**
 * @Author: Solitude
 * @Data: 2021/8/18 17:30
 * @Description:
 */
@Controller
@RequestMapping("/admin")
public class TypeController {

    @Autowired
    private TypeService typeService;

    //分页参数的默认设置，每页3条数据，按id来排序，倒叙
    @GetMapping("/types")
    public String types(@PageableDefault(size=3,sort={"id"},direction = Sort.Direction.DESC)
                                    Pageable pageable, Model model){

        //查询一页的数据
        model.addAttribute("page",typeService.listType(pageable));
        return "admin/types";
    }

    //跳转到新增的页面
    @GetMapping("/types/input")
    public String input(Model model){
        model.addAttribute("type", new Type());
        return "admin/types-input";
    }

    //增加类型types
    @PostMapping("/types")
    public String post(@Valid Type type,BindingResult result , RedirectAttributes attributes){

        Type type1 = typeService.getTypeByName(type.getName());

        if(type1 !=null){
            result.rejectValue("name","nameError","该分类已存在🥗");
        }

        if(result.hasErrors()){
            return "admin/types-input";
        }

        Type t = typeService.saveType(type);

        if(t == null){
            attributes.addFlashAttribute("message","新增失败");
        } else {
            attributes.addFlashAttribute("message","新增成功");
        }
        return "redirect:/admin/types";
    }


    //跳转到修改Type页面
    @GetMapping("/types/{id}/input")
    public String editInput(@PathVariable Long id, Model model){
        model.addAttribute("type",typeService.getType(id));
        return "admin/types-input";
    }


    //修改类型types
    @PostMapping("/types/{id}")
    public String editPost(@Valid Type type,BindingResult result ,@PathVariable Long id, RedirectAttributes attributes){

        Type type1 = typeService.getTypeByName(type.getName());

        if(type1 !=null){
            result.rejectValue("name","nameError","该分类已存在");
        }


        if(result.hasErrors()){
            return "admin/types-input";
        }


        Type t = typeService.updateType(id,type);

        if(t == null){
            attributes.addFlashAttribute("message","更新失败");
        } else {
            attributes.addFlashAttribute("message","更新成功");
        }
        return "redirect:/admin/types";
    }


    //删除Type
    @GetMapping("/types/{id}/delete")
    public String deleteType(@PathVariable Long id ,RedirectAttributes attributes){

        typeService.deleteType(id);
        attributes.addFlashAttribute("message","删除成功");
        return "redirect:/admin/types";

    }



}
