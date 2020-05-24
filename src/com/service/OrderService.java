package com.service;

import com.model.Order;

import java.util.List;

public interface OrderService {
    public void createOrder(Order order);
    public List<Order> findOrdersById(String userid);
    public Order findOrderByOrderId(String orderId);
}
