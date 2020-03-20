package wen.myblog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import wen.myblog.pojo.Tag;
import wen.myblog.service.TagService;

/**
 * 标签管理
 */
@Controller
public class AdminTagController {

    @Autowired
    private TagService tagService;

    //跳转标签页面    传递分页信息
    @GetMapping("/tag/adminTag")
    public String adminTag(@PageableDefault(size = 12,sort = {"id"},direction = Sort.Direction.DESC) Pageable pageable, Model model){
        model.addAttribute("page",tagService.getPageList(pageable));
        return "admin/adminTag";
    }

    //跳转新增页面
    @GetMapping("/tag/toAddTag")
    public String toAddTag(Model model){
        model.addAttribute("tag",new Tag());
        return "admin/adminWriteTag";
    }

    //新增标签
    @PostMapping("/tag/addTag")
    public String addTag(@Validated Tag tag, BindingResult result, RedirectAttributes attributes){
        //标签名称查重
        Tag tagByName = tagService.findByName(tag.getName().trim());
        if(tagByName != null){
            result.rejectValue("name","nameError","该标签已存在");
        }

        //后端校验
        if(result.hasErrors()){
            return "admin/adminWriteTag";
        }

        //增加标签
        Tag saveTag = tagService.saveTag(tag);
        if(saveTag == null){
            attributes.addFlashAttribute("message","保存失败");
        }else{
            attributes.addFlashAttribute("message","保存成功");
        }
        return "redirect:/tag/adminTag";
    }

    //跳转修改标签，共用新增
    @GetMapping("/tag/toUpdateTag/{id}")
    public String toUpdateTag(@PathVariable Integer id, Model model){
        model.addAttribute("tag",tagService.findById(id));
        return "admin/adminWriteTag";
    }

    //修改标签
    @PostMapping("/tag/updateTag/{id}")
    public String updateTag(@Validated Tag tag, BindingResult result,@PathVariable Integer id, RedirectAttributes attributes){
        //标签名称查重
        Tag tagByName = tagService.findByName(tag.getName().trim());
        if(tagByName != null){
            result.rejectValue("name","nameError","该标签已存在");
        }

        //后端校验,输入不能为空
        if(result.hasErrors()){
            return "admin/adminWriteTag";
        }

        //增加标签
        Tag saveTag = tagService.updateTag(id,tag);
        if(saveTag == null){
            attributes.addFlashAttribute("message","修改失败");
        }else{
            attributes.addFlashAttribute("message","修改成功");
        }
        return "redirect:/tag/adminTag";
    }

    //删除标签
    @GetMapping("/tag/del/{id}")
    public String del(@PathVariable Integer id,RedirectAttributes attributes){
        tagService.del(id);
        attributes.addFlashAttribute("message","删除成功");
        return "redirect:/tag/adminTag";
    }
}
