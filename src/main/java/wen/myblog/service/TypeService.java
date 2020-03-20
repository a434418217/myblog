package wen.myblog.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import wen.myblog.pojo.Type;

import java.util.List;

public interface TypeService {
    //分类的新增改查

    //新增
    Type saveType(Type type);

    //修改
    Type updateType(Integer id,Type type);

    //删除
    void deleteType(Integer id);

    //查询
    Page<Type> listType(Pageable pageable);

    Type getType(Integer id);

    Type getTypeByName(String name);

    //查询全部
    List<Type> list();

    //根据博客的数量进行排序
    List<Type> typeSort(Integer size);






}
