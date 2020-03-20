package wen.myblog.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import wen.myblog.pojo.Blog;

import java.util.List;

public interface BlogDao extends JpaRepository<Blog,Integer>, JpaSpecificationExecutor<Blog> {
        //查询推荐博客
    @Query("select b from Blog b where b.recommend = true ")
    List<Blog> recommendBlogs(Pageable pageable);

        //查询搜索内容
    @Query("select b from Blog b where b.title like ?1 or b.content like ?1")
    Page<Blog> findQuery(String query, Pageable pageable);

    //不展示草稿
    @Query("select b from Blog b where b.published = true ")
    Page<Blog> findBlogByPut(Pageable pageable);


    @Query("select function('date_format',b.updateTime,'%Y') as year from Blog b group by function('date_format',b.updateTime,'%Y') order by year desc ")
    List<String> findGroupYear();

    @Query("select b from Blog b where function('date_format',b.updateTime,'%Y') = ?1")
    List<Blog> findByYear(String year);
}
