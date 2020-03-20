package wen.myblog.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import wen.myblog.pojo.Tag;

import java.util.List;

public interface TagService {
    //标签类的增删改查

    //增加
    Tag saveTag(Tag tag);
    //批量储存写博客添加的标签
    List<Tag> listTag(String ids);

    //修改
    Tag updateTag(Integer id,Tag tag);

    //查询
    Page<Tag> getPageList(Pageable pageable);

    Tag findById(Integer id);

    Tag findByName(String name);

    List<Tag> findAll();

    //根据数量查询
    List<Tag> findSort(Integer size);

    //删除
    void del(Integer id);
}
