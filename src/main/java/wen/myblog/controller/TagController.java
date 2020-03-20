package wen.myblog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import wen.myblog.pojo.Tag;
import wen.myblog.service.BlogService;
import wen.myblog.service.TagService;

import java.util.List;

/**
 * 前台标签
 */
@Controller
public class TagController {

    @Autowired
    private TagService tagService;

    @Autowired
    private BlogService blogService;

    //根据id展示标签
    @GetMapping("/tags/{id}")
    public String types(@PageableDefault(size = 9,sort = {"createTime"},direction = Sort.Direction.DESC) Pageable pageable
            , Model model, @PathVariable Integer id){

        //展示10000条，不会有限制
        List<Tag> tags = tagService.findSort(100000);

        if(id == -1){
            //-1是首页发过来的
            id = tags.get(0).getId();
        }
        model.addAttribute("tags", tags);
        model.addAttribute("page", blogService.findAll(id,pageable));
        model.addAttribute("activeTagId", id);
        return "show/tags";
    }

    //跳转指定页面
    @GetMapping("/tagPage/{id}")
    public String toPage(@PathVariable Integer id,Pageable pageable, @RequestParam(defaultValue = "0") Integer page){
        Page<Tag> pageList = tagService.getPageList(pageable);
        if(page > pageList.getTotalPages()+1){
            page=pageList.getTotalPages();
        }
        if(page <= 1){
            return "redirect:/tags/"+id;
        }
        page = page-1;
        return "redirect:/tags/"+id+"?page="+page;
    }


}
