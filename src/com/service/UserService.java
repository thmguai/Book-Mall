package com.service;

import com.exception.UserException;
import com.model.User;

import java.sql.SQLException;

public interface UserService {
    public void addUser(User user) throws UserException;
    public void activeUser(String activeCode) throws UserException;
    public User loginUser(String username,String password) throws UserException;
    public User findUserInfo(String id) throws UserException;
    public void modifyUserInfo(User user) throws SQLException;
}
