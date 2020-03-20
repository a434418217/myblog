package wen.myblog.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import wen.myblog.pojo.User;

public interface LoginDao extends JpaRepository<User,Integer> {

    //查找用户名和密码
    public User findByUsernameAndPassword(String username,String password);
}
