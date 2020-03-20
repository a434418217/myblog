package wen.myblog.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import wen.myblog.pojo.Blog;
import wen.myblog.vo.BlogQuery;

import java.util.List;
import java.util.Map;

public interface BlogService {
    //博客的增删改查

    //增加
    Blog saveBlog(Blog blog);

    //修改
    Blog updateBlog(Integer id,Blog blog);

    //删除
    void del(Integer id);

    //查询单个
    Blog findOne(Integer id);

    //多条件动态查询
    Page<Blog> findAll(Pageable pageable, BlogQuery blog);
    Page<Blog> findAll(Integer id, Pageable pageable);

    //博客的归档
    Map<String,List<Blog>> historyBlog();
    //统计数目
    Long count();

    //封装分页数据
    Page<Blog> findBlog(Pageable pageable);

    //根据创建时间和是否推荐封装推荐博客
    List<Blog> findRecommend(Integer size);

    //根据字符串内容查找
    Page<Blog> findAll(String query,Pageable pageable);

    //拿到博客转化为HTML可读
    Blog getAndConvert(Integer id);
}
