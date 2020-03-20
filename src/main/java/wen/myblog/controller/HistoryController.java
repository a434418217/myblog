package wen.myblog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import wen.myblog.service.BlogService;

/**
 * 归档页
 */

@Controller
public class HistoryController {
    @Autowired
    private BlogService blogService;

    @GetMapping("/history")
    public String history(Model model) {
        model.addAttribute("historyMap", blogService.historyBlog());
        model.addAttribute("count", blogService.count());
        return "show/history";
    }
}
