package wen.myblog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import wen.myblog.pojo.Blog;
import wen.myblog.service.BlogService;
import wen.myblog.service.TagService;
import wen.myblog.service.TypeService;

/**
 * 主页
 */
@Controller
public class IndexController {

    @Autowired
    private BlogService blogService;

    @Autowired
    private TypeService typeService;

    @Autowired
    private TagService tagService;




    //跳转主页并展示数据
    @GetMapping("/")
    public String index(
            @PageableDefault(size = 9,sort = {"createTime"},direction = Sort.Direction.DESC) Pageable pageable,
                            Model model){
        Page<Blog> blog = blogService.findBlog(pageable);

        model.addAttribute("isFirst",blog.isFirst());
        model.addAttribute("isLast",blog.isLast());
        //发送blog分页数据
        model.addAttribute("page",blog);
        //发送分类信息，并在主页排序（根据分类里文章数目来排）
        model.addAttribute("types",typeService.typeSort(7));
        //发送标签信息，并在主页排序（根据分类里文章数目来排）
        model.addAttribute("tags",tagService.findSort(9));
        //发送推荐博客
        model.addAttribute("recomendBlogs", blogService.findRecommend(8));
        return "show/index";
    }

    //页码跳转
    @GetMapping("/page")
    public String toPage(@RequestParam(defaultValue = "0") Integer page,Pageable pageable){
        Page<Blog> blog = blogService.findBlog(pageable);

        if(page > blog.getTotalPages()+1){
            page=blog.getTotalPages();
        }
        if(page <= 1){
            return "redirect:/";
        }
        page = page-1;
        return "redirect:/?page="+page;
    }


}
