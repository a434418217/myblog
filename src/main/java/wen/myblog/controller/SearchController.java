package wen.myblog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import wen.myblog.pojo.Blog;
import wen.myblog.service.BlogService;

/**
 * 搜索页面
 */
@Controller
public class SearchController {
    @Autowired
    private BlogService blogService;

    //展示搜索结果
    @RequestMapping("/search")
    public String search(@PageableDefault(size = 9,sort = {"createTime"},direction = Sort.Direction.DESC) Pageable pageable
            , Model model, String query){
        model.addAttribute("page",blogService.findAll("%"+query+"%",pageable));
        //搜索框显示搜索内容
        model.addAttribute("query",query);
        return "show/search";
    }

    //搜索结果分页
    /**
     *
     * @param page
     * @param query
     * @param attributes    重定向时会拼装参数
     * @return
     */
    @RequestMapping("/Searchpage")
    public String page(@RequestParam("page") Integer page, @RequestParam("query") String query, RedirectAttributes attributes,
                       @PageableDefault(size = 9,sort = {"createTime"},direction = Sort.Direction.DESC) Pageable pageable){
        Page<Blog> blogs = blogService.findAll("%" + query + "%", pageable);
        if(page < 1){
            page = 1;
        }
        if(page > blogs.getTotalPages()+1){
            page=blogs.getTotalPages();
        }
        attributes.addFlashAttribute("query",query);
        attributes.addAttribute("page",page-1);
        return "redirect:/search";
    }
}
