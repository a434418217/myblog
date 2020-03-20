package wen.myblog.service;

import wen.myblog.pojo.User;

public interface Login {
    //校检用户名和密码
    public User checkUser(String username,String password);
}
