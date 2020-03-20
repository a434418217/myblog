package wen.myblog.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wen.myblog.dao.TypeDao;
import wen.myblog.exception.NotFoundExcepiton;
import wen.myblog.pojo.Type;

import java.util.List;

@Service
public class TypeServiceImpl implements TypeService {

    @Autowired
    private TypeDao typeDao;

    //查询
    @Override
    public Page<Type> listType(Pageable pageable) {
        return typeDao.findAll(pageable);
    }

    @Override
    public Type getType(Integer id) {
        return typeDao.getOne(id);
    }

    @Override
    public Type getTypeByName(String name) {
        return typeDao.findByName(name);
    }

    @Override
    public List<Type> list() {
        return typeDao.findAll();
    }

    @Override
    public List<Type> typeSort(Integer size) {
        //由大到小排序博客数量
        Sort sort = Sort.by(Sort.Direction.DESC, "blogs.size");
        PageRequest pageRequest = PageRequest.of(0, size, sort);
        return typeDao.findSort(pageRequest);
    }

    //保存
    @Transactional
    @Override
    public Type saveType(Type type) {
        return typeDao.save(type);
    }

    //修改
    @Transactional
    @Override
    public Type updateType(Integer id, Type type) {
        Type t = typeDao.getOne(id);
        if (t == null) {
            throw new NotFoundExcepiton("不存在该类型");
        }
        BeanUtils.copyProperties(type,t);
        return typeDao.save(t);
    }

    //删除
    @Override
    public void deleteType(Integer id) {
        typeDao.deleteById(id);
    }

}
