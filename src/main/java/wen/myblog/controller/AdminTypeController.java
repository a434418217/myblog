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
import wen.myblog.pojo.Type;
import wen.myblog.service.TypeService;

/**
 * 分类管理
 */
@Controller
public class AdminTypeController {

    @Autowired
    private TypeService typeService;

    //查看分类列表，传递分页信息
    @GetMapping("/type/adminType")
    public String adminType(@PageableDefault(size = 12,sort = {"id"},direction = Sort.Direction.DESC)
                                Pageable pageable, Model model) {
        model.addAttribute("page",typeService.listType(pageable));
        return "admin/adminType";
    }

    //跳转到新增页面
    @GetMapping("/type/toAddType")
    public String toAddType(Model model){
        model.addAttribute("type",new Type());
        return "admin/adminWriteType";
    }

    //保存新增的数据
    @PostMapping("/type/addType")
    public String addType(@Validated Type type, BindingResult result, RedirectAttributes attributes){

        //分类名称重复
        Type typeByName = typeService.getTypeByName(type.getName().trim());
        if(typeByName != null){     //查到了名称重复
            result.rejectValue("name","nameError","该分类已存在");
        }
        //后端校验
        if (result.hasErrors()){
            return "admin/adminWriteType";
        }

        Type t = typeService.saveType(type);
        if (t == null){
            attributes.addFlashAttribute("message","保存失败");
        }else{
            attributes.addFlashAttribute("message","保存成功");
        }
        return "redirect:/type/adminType";
    }

    //修改分类和新增共用一个页面
    @GetMapping("/type/getUpdateType/{id}")
    public String getUpdateType(@PathVariable Integer id, Model model){
        model.addAttribute("type",typeService.getType(id));
        return "admin/adminWriteType";
    }

    //修改的数据
    @PostMapping("/type/updateType/{id}")
    public String updateType(@Validated Type type, BindingResult result, @PathVariable Integer id, RedirectAttributes attributes){

        //分类名称重复
        Type typeByName = typeService.getTypeByName(type.getName());
        if(typeByName != null){     //查到了名称重复
            result.rejectValue("name","nameError","该分类已存在");
        }
        //后端校验
        if (result.hasErrors()){
            return "admin/adminWriteType";
        }

        Type t = typeService.updateType(id,type);
        if (t == null){
            attributes.addFlashAttribute("message","修改失败");
        }else{
            attributes.addFlashAttribute("message","修改成功");
        }
        return "redirect:/type/adminType";
    }

    //删除数据
    @GetMapping("/type/del/{id}")
    public String del(@PathVariable Integer id,RedirectAttributes attributes){
        typeService.deleteType(id);
        attributes.addFlashAttribute("message","删除成功");
        return "redirect:/type/adminType";
    }
}
