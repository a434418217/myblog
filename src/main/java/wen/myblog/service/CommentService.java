package wen.myblog.service;

import wen.myblog.pojo.Comment;

import java.util.List;

public interface CommentService {
    //根据id查询评论
    List<Comment> listCommentByBlogId(Integer blogId);
    //保存
    Comment saveComment(Comment comment);
    //删除
    void delComment(Integer id);
}
