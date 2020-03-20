package wen.myblog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import wen.myblog.service.BlogService;

/**
 * 博客详情页
 */
@Controller
public class BlogController {

    @Autowired
    private BlogService blogService;

    //发送博客数据
    @GetMapping("/readBlog/{id}")
    public String blog(@PathVariable Integer id, Model model){
        model.addAttribute("blog",blogService.getAndConvert(id));
        return "show/blog";
    }
}
