package wen.myblog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wen.myblog.dao.MessageDao;
import wen.myblog.pojo.Message;

import java.util.List;

@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    private MessageDao messageDao;

    //查询所有评论
    @Transactional
    @Override
    public List<Message> findAll() {
        Sort sort = Sort.by("createTime");
        return messageDao.findAll(sort);

    }

    //删除
    @Override
    public void del(Integer id) {
        messageDao.deleteById(id);
    }

    //保存
    @Transactional
    @Override
    public Message saveMessage(Message message) {
        return messageDao.save(message);
    }


}
