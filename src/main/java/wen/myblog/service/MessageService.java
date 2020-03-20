package wen.myblog.service;

import wen.myblog.pojo.Message;

import java.util.List;

public interface MessageService {
    //保存留言
    Message saveMessage(Message message);

    //查看留言
    List<Message> findAll();

    //删除
    void del(Integer id);
}
