package wen.myblog.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wen.myblog.dao.TagDao;
import wen.myblog.exception.NotFoundExcepiton;
import wen.myblog.pojo.Tag;

import java.util.ArrayList;
import java.util.List;

@Service
public class TagServiceImpl implements TagService {

    @Autowired
    private TagDao tagDao;

    //查询分割后的标签名查询标签
    @Transactional
    @Override
    public List<Tag> listTag(String ids) {
        return tagDao.findAllById(convertToList(ids));
    }
    private Iterable<Integer> convertToList(String ids) {
        List<Integer> list = new ArrayList<>();
        if (!"".equals(ids) && ids != null) {
            String[] idarray = ids.split(",");
            for (int i=0; i < idarray.length;i++) {
                list.add(new Integer(idarray[i]));
            }
        }
        return list;
    }

    //分装标签分页信息
    @Override
    public Page<Tag> getPageList(Pageable pageable) {
        return tagDao.findAll(pageable);
    }

    @Override
    public Tag findById(Integer id) {
        return tagDao.getOne(id);
    }

    @Override
    public Tag findByName(String name) {
        return tagDao.findByName(name);
    }

    @Override
    public List<Tag> findAll() {
        return tagDao.findAll();
    }

    @Override
    public List<Tag> findSort(Integer size) {
        //由大到小排序博客数量
        Sort sort = Sort.by(Sort.Direction.DESC, "blogs.size");
        PageRequest pageRequest = PageRequest.of(0, size, sort);
        return tagDao.findSort(pageRequest);
    }

    //保存标签
    @Transactional
    @Override
    public Tag saveTag(Tag tag) {
        return tagDao.save(tag);
    }

    //修改标签
    @Transactional
    @Override
    public Tag updateTag(Integer id,Tag tag) {
        Tag t = tagDao.getOne(id);
        if(t == null){
            throw new NotFoundExcepiton("该标签不存在");
        }
        BeanUtils.copyProperties(tag,t);
        return t;
    }


    @Transactional
    @Override
    public void del(Integer id) {
        tagDao.deleteById(id);
    }
}
