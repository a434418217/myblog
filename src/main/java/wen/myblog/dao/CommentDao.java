package wen.myblog.dao;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import wen.myblog.pojo.Comment;

import java.util.List;

public interface CommentDao extends JpaRepository<Comment,Integer> {
    //根据id查找评论并排序
    List<Comment> findByBlogIdAndParentCommentNull(Integer blogId, Sort sort);
}
