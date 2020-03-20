package wen.myblog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import wen.myblog.pojo.Blog;
import wen.myblog.pojo.User;
import wen.myblog.service.BlogService;
import wen.myblog.service.TagService;
import wen.myblog.service.TypeService;
import wen.myblog.vo.BlogQuery;

import javax.servlet.http.HttpSession;

/**
 * 管理博客
 */
@Controller
public class AdminBlogController {

    @Autowired
    private BlogService blogService;

    @Autowired
    private TypeService typeService;

    @Autowired
    private TagService tagService;


    //博客管理页
    @GetMapping("/blog/blogs")
    public String adminBlog(@PageableDefault(size = 12,sort = {"updateTime"},direction = Sort.Direction.DESC) Pageable pageable
            , BlogQuery blog, Model model){
        //发送分类数据过去作为条件
        model.addAttribute("types",typeService.list());
        model.addAttribute("page",blogService.findAll(pageable,blog));
        return "admin/adminBlog";
    }


    //局部刷新，只更新了blogList内容
    @PostMapping("/blog/search")
    public String search(@PageableDefault(size = 12, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable,
                         BlogQuery blog, Model model) {
        model.addAttribute("page", blogService.findAll(pageable, blog));
        return "admin/adminBlog :: blogList";
    }



//    跳转到写博客页面
    @GetMapping("/blog/writeBlog")
    public String writeBlog(Model model){
        //发送分类数据过去作为条件
        model.addAttribute("types",typeService.list());
        model.addAttribute("tags",tagService.findAll());
        model.addAttribute("blog",new Blog());
        return "admin/adminWriteBlog";
    }

    //保存和修改所写博客
    @PostMapping("/blog/saveBlog")
    public String saveBlog(Blog blog,RedirectAttributes attributes, HttpSession session){
        //从session域中存入User信息到blog中
        blog.setUser((User) session.getAttribute("user"));
        blog.setType(typeService.getType(blog.getType().getId()));
        blog.setTags(tagService.listTag(blog.getTagIds()));
        Blog b;

        //设置默认为原创
        if(blog.getFlag() == null || blog.getFlag().equals("")){
            blog.setFlag("原创");
        }

        if(blog.getId() != null){
            //传值有id为修改
            b = blogService.updateBlog(blog.getId(),blog);
        }else{
            //传值无id为删除
            b = blogService.saveBlog(blog);
        }

        if (b == null){
            attributes.addFlashAttribute("message","保存失败");
        }else{
            attributes.addFlashAttribute("message","保存成功");
        }
        return "redirect:/blog/blogs";
    }

    //跳转到修改博客页面，与保存页面共同
    @GetMapping("/blog/toUpdateBlog/{id}")
    public String toUpdateBlog(@PathVariable Integer id, Model model){
        //发送分类数据过去作为条件
        model.addAttribute("types",typeService.list());
        model.addAttribute("tags",tagService.findAll());
        Blog blog = blogService.findOne(id);
        blog.init();
        model.addAttribute("blog",blog);
        return "admin/adminWriteBlog";
    }

    //删除
    @GetMapping("/blog/del/{id}")
    public String del(@PathVariable Integer id,RedirectAttributes attributes ){
        blogService.del(id);
        attributes.addFlashAttribute("message","删除成功");
        return "redirect:/blog/blogs";
    }

}
