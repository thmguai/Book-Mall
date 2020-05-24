package com.service.impl;


import com.dao.UserDao;
import com.dao.impl.UserDaoImpl;
import com.exception.UserException;
import com.model.User;
import com.service.UserService;
import com.utils.C3P0Utils;
import com.utils.SendJMail;
import org.apache.commons.dbutils.QueryRunner;

import java.sql.SQLException;

public class UserServiceImpl implements UserService {
    UserDao userDao = new UserDaoImpl();
    @Override
    public void addUser(User user) throws UserException {

        try {
            userDao.addUser(user);
            String link = "http://localhost:8080/activeServlet?activeCode=" + user.getActiveCode();
            String html = "<a href=\""+link+"\">欢迎您注册网上书城账号，请点击激活</a>";
            //发送激活邮件
            System.out.println(html);
            SendJMail.sendMail(user.getEmail(), html);
        }catch (Exception e){
            e.printStackTrace();
            throw new UserException("用户注册失败，用户名重复");
        }
    }

    @Override
    public void activeUser(String activeCode) throws UserException {
        //查询激活码用户是否存在
        try {
            User user = userDao.findUserByActiveCode(activeCode);
            if (user == null){
                throw new UserException("非法激活");
            }
            if ((user != null) && (user.getState() == 1)){
                throw new UserException("用户已激活");
            }
            userDao.updateState(activeCode);
        } catch (SQLException e) {
            throw new UserException("激活失败");
        }
    }

    @Override
    public User loginUser(String username, String password) throws UserException {
        try {
            User user = userDao.findUserByUsernameAndPassword(username, password);
            if (user == null){
                throw new UserException("用户名或密码错误");
            }
            if (user.getState() == 0){
                throw new UserException("用户未激活，请先登入邮箱激活");
            }
            return user;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new UserException("登入失败");
        }
    }

    @Override
    public User findUserInfo(String id) throws UserException {
        try {
            User user = userDao.findUserById(id);
            if (user == null){
                throw new UserException("用户名不存在");
            }
            return user;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new UserException("未知错误");
        }
    }


    @Override
    public void modifyUserInfo(User user) throws SQLException {
        userDao.modifyUser(user);
    }


}
