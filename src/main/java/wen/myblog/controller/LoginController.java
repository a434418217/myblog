package wen.myblog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import wen.myblog.pojo.User;
import wen.myblog.service.Login;

import javax.servlet.http.HttpSession;

@Controller
public class LoginController {



    @Autowired
    private Login login;

    @GetMapping("/admin")
    public String login(){
        return "admin/login";
    }


    //检查用户名和密码
    @RequestMapping(value = "/admin/adminBlog")
    public String checkOut(@RequestParam(required = false) String username, @RequestParam(required = false) String password,
                           HttpSession session, RedirectAttributes redirectAttributes){

        //防止直接访问/adminBlog，传空参
        if(username == null){
            redirectAttributes.addFlashAttribute("msg","用户名或密码错误");
            return "redirect:/admin";
        }

           User user = login.checkUser(username, password);
        if(user != null){
            //session不存密码，危险
            user.setPassword(null);
            session.setAttribute("user",user);
            return "redirect:/blog/blogs";
        }else{
            redirectAttributes.addFlashAttribute("msg","用户名或密码错误");
            return "redirect:/admin";
        }

    }

    //注销
    @GetMapping("/admin/loginOut")
    public String loginOut(HttpSession session){
        //注销时清空session
        session.removeAttribute("user");
        return "redirect:/admin";
    }

}
