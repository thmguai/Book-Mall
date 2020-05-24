package com.service.impl;

import com.dao.OrderDao;
import com.dao.impl.OrderDaoImpl;
import com.model.Order;
import com.model.OrderItem;
import com.service.OrderService;
import com.utils.ManagerThreadLocal;

import java.sql.SQLException;
import java.util.List;

public class OrderServiceImpl implements OrderService{
    OrderDao orderDao = new OrderDaoImpl();


    @Override
    public void createOrder(Order order){
        try {
            //开启事务
            ManagerThreadLocal.beginTransaction();
            orderDao.addOrder(order);
            orderDao.addOrderItem(order);
            for (OrderItem item : order.getItems()) {
                orderDao.updatePnum(item.getProduct().getId(),item.getBuynum());
            }
            //结束事务
            ManagerThreadLocal.commitTransaction();
        } catch (SQLException e) {
            e.printStackTrace();
            //回滚事务
            ManagerThreadLocal.rollback();
        }

    }

    @Override
    public List<Order> findOrdersById(String userid) {
        try {
            return orderDao.findOrdersById(userid);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Order findOrderByOrderId(String orderId) {
        try {
            return orderDao.findOrderByOrderId(orderId);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }


}
