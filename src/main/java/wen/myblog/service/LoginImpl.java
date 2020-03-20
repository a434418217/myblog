package wen.myblog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wen.myblog.dao.LoginDao;
import wen.myblog.pojo.User;
import wen.myblog.util.MD5Utils;

@Service
public class LoginImpl implements Login {

    @Autowired
    private LoginDao loginDao;

    //查询管理员账户，将密码封装成MD5与数据库的MD5进行匹配
    @Override
    public User checkUser(String username, String password) {
        return loginDao.findByUsernameAndPassword(username, MD5Utils.code(password));
    }
}
