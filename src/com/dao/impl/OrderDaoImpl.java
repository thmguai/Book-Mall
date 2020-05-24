package com.dao.impl;

import com.dao.OrderDao;
import com.model.Order;
import com.model.OrderItem;
import com.model.Product;
import com.utils.C3P0Utils;
import com.utils.ManagerThreadLocal;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderDaoImpl implements OrderDao {
    @Override
    public void addOrder(Order order) throws SQLException {
        String sql = "insert into orders values (?,?,?,?,?,?,?,?)";
        List<Object> list = new ArrayList<>();
        list.add(order.getId());
        list.add(order.getMoney());
        list.add(order.getReceiveAddress());
        list.add(order.getReceiveName());
        list.add(order.getReceivePhone());
        list.add(order.getPaystate());
        list.add(order.getUser().getId());
        list.add(order.getOrderTime());
        /*QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());
        qr.update(sql,list.toArray());*/
        //开启事务
        QueryRunner qr = new QueryRunner();
        qr.update(ManagerThreadLocal.getConnection(),sql,list.toArray());
    }

    @Override
    public void addOrderItem(Order order) throws SQLException {

        String sql = "insert into orderitem (order_id, product_id, buynum) values (?,?,?)";
        QueryRunner qr = new QueryRunner();
        for (OrderItem item : order.getItems()){
            qr.update(ManagerThreadLocal.getConnection(),sql,item.getOrder().getId(),item.getProduct().getId(),item.getBuynum());
        }

    }

    @Override
    public void updatePnum(String id, int num) throws SQLException {
        String sql = "update products set pnum=pnum-? where id=? ";
        /*QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());
        qr.update(sql,num,id);*/
        QueryRunner qr = new QueryRunner();
        qr.update(ManagerThreadLocal.getConnection(),sql,num,id);
    }

    @Override
    public List<Order> findOrdersById(String userid) throws SQLException {
        QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());
        String sql = "select * from orders where user_id = ?";
        return qr.query(sql, new BeanListHandler<Order>(Order.class),Integer.parseInt(userid));
    }

    @Override
    public Order findOrderByOrderId(String orderId) throws SQLException {
        String sql1 = "select * from orders where id=?";
        QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());
        Order order = qr.query(sql1, new BeanHandler<Order>(Order.class),orderId);

        String sql2 = "SELECT o.*, p.NAME ,p.price FROM orderitem o, products p " +
                "WHERE order_id = ? AND p.id=o.product_id";
        List<OrderItem> mItems = qr.query(sql2, new ResultSetHandler<List<OrderItem>>() {
            @Override
            public List<OrderItem> handle(ResultSet resultSet) throws SQLException {
                List<OrderItem> items = new ArrayList<>();
                while (resultSet.next()) {
                    OrderItem item = new OrderItem();
                    item.setBuynum(resultSet.getInt("buynum"));

                    Product p = new Product();
                    p.setId(resultSet.getString("product_id"));
                    p.setName(resultSet.getString("name"));
                    p.setPrice(resultSet.getDouble("price"));

                    item.setProduct(p);

                    items.add(item);

                }
                return items;
            }
        },orderId);
        order.setItems(mItems);
        return order;
    }

    public static void main(String[] args) throws SQLException {
        OrderDao orderDao = new OrderDaoImpl();
        Order orderByOrderId = orderDao.findOrderByOrderId("e2a1b72b-b990-44b1-9f11-205074347bf5");
        System.out.println(orderByOrderId);
    }
}
