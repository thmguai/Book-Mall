package com.dao;

import com.exception.UserException;
import com.model.User;

import java.sql.SQLException;

public interface UserDao {
    public void addUser(User user) throws SQLException;
    public User findUserByActiveCode(String activeCode) throws SQLException;
    public void updateState(String activeCode) throws SQLException;
    public User findUserByUsernameAndPassword(String username,String password) throws SQLException;
    public User findUserById(String id) throws SQLException;
    public void modifyUser(User user) throws SQLException;

}
