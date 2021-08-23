package com.lst.web.admin;

import com.lst.pojo.Tag;
import com.lst.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

/**
 * @Author: Solitude
 * @Data: 2021/8/19 20:06
 * @Description:
 */
@Controller
@RequestMapping("/admin")
public class TagController {

    @Autowired
    private TagService tagService;

    //分页参数的默认设置，每页3条数据，按id来排序，倒叙
    @GetMapping("tags")
    public String tags(@PageableDefault(size=3,sort={"id"},direction = Sort.Direction.DESC)
                       Pageable pageable, Model model){

        //查询一页的数据
        model.addAttribute("page",tagService.listTag(pageable));
        return "admin/tags";
    }


    //跳转到新增页面
    @GetMapping("/tags/input")
    public String input(Model model){
        model.addAttribute("tag",new Tag());
        return "admin/tags-input";
    }


    //增加标签tag
    @PostMapping("/tags")
    public String post(@Valid Tag tag, BindingResult result, RedirectAttributes attributes){
        Tag tag1 = tagService.getTagByName(tag.getName());
        if( tag1!=null ){
            result.rejectValue("name","nameError","该标签🌼已存在");
        }
        if(result.hasErrors()){
            return "admin/tags-input";
        }

        Tag t = tagService.saveTag(tag);

        if( t==null ){
            attributes.addFlashAttribute("message","新增失败");
        } else {
            attributes.addFlashAttribute("message","新增成功");
        }
        return "redirect:/admin/tags";
    }



    //跳转到修改Tag页面
    @GetMapping("/tags/{id}/input")
    public String editTag(@PathVariable Long id,Model model){
        model.addAttribute("tag",tagService.getTag(id));
        return "admin/tags-input";
    }



    //修改标签tags
    @PostMapping("/tags/{id}")
    public String editPost(@Valid Tag tag,BindingResult result,@PathVariable Long id, RedirectAttributes attributes){


        //bug : 刷新不及时
        Tag tag1 = tagService.getTagByName(tag.getName());

        if( tag!=null ){
            result.rejectValue("name","nameError","该标签已存在");
        }
        if(result.hasErrors()){
            return "admin/tags-input";
        }
        Tag t = tagService.updateTag(id, tag);
        if(t == null){
            attributes.addFlashAttribute("message","更新失败");
        } else {
            attributes.addFlashAttribute("message","更新成功");
        }
        return "redirect:/admin/tags";
    }



    //删除tag
    @GetMapping("/tags/{id}/delete")
    public String deleteTag(@PathVariable Long id,RedirectAttributes attributes){

        tagService.deleteTag(id);
        attributes.addFlashAttribute("message","删除成功");
        return "redirect:/admin/tags";
    }

}
