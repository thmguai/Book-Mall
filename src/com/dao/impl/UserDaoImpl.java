package com.dao.impl;

import java.sql.SQLException;

import com.dao.UserDao;
import com.exception.UserException;
import com.model.User;
import com.utils.C3P0Utils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;


public class UserDaoImpl implements UserDao {

	public void addUser(User user) throws SQLException{
		QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());
		String sql = "INSERT INTO user " + 
					 "(username,password,gender,email,telephone,introduce,activeCode,state,role,registTime) " +
				     "VALUES (?,?,?,?,?,?,?,?,?,?)";
					
		qr.update(sql,user.getUsername(),user.getPassword(),user.getGender(),
				user.getEmail(),user.getTelephone(),user.getIntroduce(),
				user.getActiveCode(),user.getState(),user.getRole(),user.getRegistTime());
	}

	@Override
	public User findUserByActiveCode(String activeCode) throws SQLException {
		QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());
		String sql = "select * from user where activeCode=?";
		User user = qr.query(sql, new BeanHandler<User>(User.class),activeCode);
		return user;
	}

	@Override
	public void updateState(String activeCode) throws SQLException {
		QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());

		String sql = "update user set state=1 where activeCode=?";
		qr.update(sql,activeCode);
	}

	@Override
	public User findUserByUsernameAndPassword(String username, String password) throws SQLException {
		QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());
		String sql = "select * from user where username=? and password=?";
		User user = qr.query(sql, new BeanHandler<User>(User.class), username, password);
		return user;
	}

	@Override
	public User findUserById(String id) throws SQLException {
		QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());
		String sql = "select * from user where id=?";
		User user = qr.query(sql, new BeanHandler<User>(User.class), id);
		return user;
	}

	@Override
	public void modifyUser(User user) throws SQLException {
		QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());
		String sql = "update user set password=?, gender=?, telephone=?  where id=?";
		qr.update(sql,user.getPassword(),user.getGender(),user.getTelephone(),user.getId());
	}


}
