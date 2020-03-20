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
import wen.myblog.pojo.Type;
import wen.myblog.service.BlogService;
import wen.myblog.service.TypeService;
import wen.myblog.vo.BlogQuery;

import java.util.List;

/**
 * 前台分类
 */
@Controller
public class TypeController {

    @Autowired
    private TypeService typeService;

    @Autowired
    private BlogService blogService;

    //根据id查询分类信息
    @GetMapping("/types/{id}")
    public String types( @PageableDefault(size = 9,sort = {"createTime"},direction = Sort.Direction.DESC) Pageable pageable
            , Model model, @PathVariable Integer id){

        //展示10000条，不会有限制
        List<Type> types = typeService.typeSort(100000);

        if(id == -1){
            //-1是首页发过来的
            id = types.get(0).getId();
        }
        BlogQuery blogQuery = new BlogQuery();
        blogQuery.setTypeId(id);

        model.addAttribute("types", types);
        model.addAttribute("page", blogService.findAll(pageable, blogQuery));
        //当前的标签id
        model.addAttribute("activeTypeId", id);
        return "show/types";
    }

    //跳转指定页面
    @GetMapping("/typePage/{id}")
    public String toPage(@RequestParam(defaultValue = "0") Integer page,Pageable pageable,@PathVariable Integer id){
        Page<Type> types = typeService.listType(pageable);
        if(page > types.getTotalPages()+1){
            page=types.getTotalPages();
        }
        if(page <= 1){
            return "redirect:/types/"+id;
        }
        page = page-1;
        return "redirect:/types/"+id+"?page="+page;
    }

}
