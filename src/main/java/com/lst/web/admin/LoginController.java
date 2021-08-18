package com.lst.web.admin;

import com.lst.pojo.User;
import com.lst.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

/**
 * @Author: Solitude
 * @Data: 2021/8/18 13:29
 * @Description:
 */
@Controller
@RequestMapping("/admin")
public class LoginController {
    @Autowired
    private UserService userService;

    //跳转到登陆页面
    @GetMapping
    public String loginPage(){
        return "admin/login";
    }

    //判断登陆
    @PostMapping("/login")
    public String login(@RequestParam String username,
                        @RequestParam String password,
                        HttpSession session,
                        RedirectAttributes attributes){
        User user = userService.checkUser(username, password);
        if ((user !=null)) {
            user.setPassword(null);  //不要将密码传到前端
            session.setAttribute("user",user);
            return "admin/index";
        } else {
            attributes.addFlashAttribute("message","用户名或密码错误");
            return "redirect:/admin";  //不能直接return "admin/login" ;否则再次登陆就会有问题
        }
    }

    //退出登陆
    @GetMapping("/logout")
    public String logout(HttpSession session){
        session.removeAttribute("user");
        return "redirect:/admin";
    }

}
