package com.dao;

import com.model.Order;

import java.sql.SQLException;
import java.util.List;

public interface OrderDao {
    public void addOrder(Order order) throws SQLException;
    public void addOrderItem(Order order) throws SQLException;
    public void updatePnum(String id,int num) throws SQLException;
    public List<Order> findOrdersById(String userid) throws SQLException;
    public Order findOrderByOrderId(String orderId) throws SQLException;
}
