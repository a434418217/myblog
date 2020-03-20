package wen.myblog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import wen.myblog.pojo.Comment;
import wen.myblog.pojo.User;
import wen.myblog.service.BlogService;
import wen.myblog.service.CommentService;

import javax.servlet.http.HttpSession;

/**
 * 评论
 */
@Controller
public class CommentController {
    @Autowired
    private CommentService commentService;

    @Autowired
    private BlogService blogService;

    //设置默认用户头像
    @Value("${comment.avatar}")
    private String avatar;

    //显示评论信息
    @GetMapping("/comments/{blogId}")
    public String comments(@PathVariable Integer blogId, Model model) {
        model.addAttribute("comments", commentService.listCommentByBlogId(blogId));
        return "show/blog :: commentList";
    }


    //保存评论信息
    @RequestMapping("/comments")
    public String post(Comment comment, HttpSession session) {
        Integer blogId = comment.getBlog().getId();
        comment.setBlog(blogService.findOne(blogId));
        User user = (User) session.getAttribute("user");
        if (user != null) {
            comment.setAvatar(user.getAvatar());
            comment.setAdminComment(true);
        } else {
            comment.setAvatar(avatar);
        }
        commentService.saveComment(comment);
        return "redirect:/comments/" + blogId;
    }

    //管理员登录下显示删除评论
    @GetMapping("/del/{cid}/{bid}")
    public String delComment(@PathVariable Integer cid,@PathVariable Integer bid, HttpSession session,Model model){
        User user = (User) session.getAttribute("user");
        if(user != null){
            commentService.delComment(cid);
        }
        model.addAttribute("comments", commentService.listCommentByBlogId(bid));
        return "redirect:/readBlog/" + bid;
    }

}
