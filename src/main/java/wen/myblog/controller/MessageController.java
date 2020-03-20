package wen.myblog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import wen.myblog.pojo.Message;
import wen.myblog.pojo.User;
import wen.myblog.service.MessageService;

import javax.servlet.http.HttpSession;

/**
 * 公共留言
 */
@Controller
public class MessageController {

    @Autowired
    private MessageService messageService;

    //设置默认用户头像
    @Value("${comment.avatar}")
    private String avatar;

    //显示留言信息
    @GetMapping("/message")
    public String message( Model model) {
        model.addAttribute("messages",messageService.findAll());
        return "show/message";
    }

    //局部刷新留言信息
    @GetMapping("/messageList")
    public String messageList( Model model) {
        model.addAttribute("messages",messageService.findAll());
        return "show/message :: commentList";
    }

    //保存留言信息
    @RequestMapping("/messages")
    public String post(Message message, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user != null) {
            message.setAvatar(user.getAvatar());
            message.setAdminComment(true);
        } else {
            message.setAvatar(avatar);
        }
        messageService.saveMessage(message);
        return "redirect:/messageList";
    }

    //删除
    @GetMapping("/del/{id}")
    public String del(@PathVariable Integer id,HttpSession session){
        User user = (User) session.getAttribute("user");
        if(user != null){
            messageService.del(id);
        }
        return "redirect:/message";
    }

}
