package wen.myblog.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wen.myblog.dao.BlogDao;
import wen.myblog.exception.NotFoundExcepiton;
import wen.myblog.pojo.Blog;
import wen.myblog.pojo.Type;
import wen.myblog.util.MarkdownUtils;
import wen.myblog.util.MyBeanUtils;
import wen.myblog.vo.BlogQuery;

import javax.persistence.criteria.*;
import java.util.*;

@Service
public class BlogServiceImpl implements BlogService {

    @Autowired
    private BlogDao blogDao;


    //根据id查询博客
    @Override
    public Blog findOne(Integer id) {
        return blogDao.getOne(id);
    }

    //封装搜索条件
    @Transactional
    @Override
    public Page<Blog> findAll(Pageable pageable, BlogQuery blog) {
        return blogDao.findAll(new Specification<Blog>() {
            @Override
            public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<>();
                if (!"".equals(blog.getTitle()) && blog.getTitle() != null) {
                    predicates.add(cb.like(root.<String>get("title"), "%"+blog.getTitle()+"%"));
                }
                if (blog.getTypeId() != null) {
                    predicates.add(cb.equal(root.<Type>get("type").get("id"), blog.getTypeId()));
                }
                if (blog.isRecommend()) {
                    predicates.add(cb.equal(root.<Boolean>get("recommend"), blog.isRecommend()));
                }
                cq.where(predicates.toArray(new Predicate[predicates.size()]));
                return null;
            }
        },pageable);
    }

    //查询不同tag的id并封装
    @Override
    public Page<Blog> findAll(Integer id, Pageable pageable) {
        return blogDao.findAll(new Specification<Blog>() {
            @Override
            public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Join join = root.join("tags");
                return criteriaBuilder.equal(join.get("id"),id);
            }
        },pageable);
    }

    //博客的归档
    @Override
    public Map<String, List<Blog>> historyBlog() {
        List<String> years = blogDao.findGroupYear();
        Map<String, List<Blog>> map = new HashMap<>();
        for (String year : years) {
            map.put(year, blogDao.findByYear(year));
        }
        return map;
    }

    //查询博客总数量
    @Override
    public Long count() {
        return blogDao.count();
    }

    //封装博客分页信息
    @Override
    public Page<Blog> findBlog(Pageable pageable) {
        return blogDao.findBlogByPut(pageable);
    }

    //根据创建时间封装推荐博客
    @Override
    public List<Blog> findRecommend(Integer size) {
        Sort sort = Sort.by(Sort.Direction.DESC, "createTime");
        PageRequest pageRequest = PageRequest.of(0, size, sort);
        return blogDao.recommendBlogs(pageRequest);
    }

    //根据字符串内容查找
    @Override
    public Page<Blog> findAll(String query, Pageable pageable) {
        return blogDao.findQuery(query, pageable);
    }

    //根据id修改博客
    @Transactional
    @Override
    public Blog updateBlog(Integer id, Blog blog) {
        Blog one = blogDao.getOne(id);
        if(one == null){
            throw new NotFoundExcepiton("数据库中没有这个博客，无法修改");
        }
        blog.setUpdateTime(new Date());
        BeanUtils.copyProperties(blog,one, MyBeanUtils.getNullPropertyNames(blog));
        return blogDao.save(one);
    }

    //保存博客
    @Transactional
    @Override
    public Blog saveBlog(Blog blog) {
        blog.setCreateTime(new Date());
        blog.setUpdateTime(new Date());
        return blogDao.save(blog);
    }

    //删除博客
    @Override
    public void del(Integer id) {
        blogDao.deleteById(id);
    }

    //拿到博客转化为HTML可读
    @Override
    public Blog getAndConvert(Integer id) {
        Blog blog = blogDao.getOne(id);
        if(blog == null){
            throw new NotFoundExcepiton("此博客不存在");
        }
        //由于blog的修改直接操作数据库数据，在修改博客时会影响数据完整，所以复制一个来使用
        Blog b = new Blog();
        BeanUtils.copyProperties(blog,b);
        String content = b.getContent();
        b.setContent(MarkdownUtils.markdownToHtmlExtensions(content));
        return b;
    }
}
