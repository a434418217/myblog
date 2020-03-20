package wen.myblog.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import wen.myblog.pojo.Message;

public interface MessageDao extends JpaRepository<Message,Integer> {
}
