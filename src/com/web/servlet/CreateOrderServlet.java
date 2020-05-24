package com.web.servlet;

import com.model.Order;
import com.model.OrderItem;
import com.model.Product;
import com.model.User;
import com.service.OrderService;
import com.service.impl.OrderServiceImpl;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

@WebServlet("/createOrderServlet")
public class CreateOrderServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            //封装Order
            User user = (User) request.getSession().getAttribute("user");
            if (user == null){
                response.getWriter().write("非法访问...");
                return;
            }
            Map<Product,Integer> cart = (Map<Product, Integer>) request.getSession().getAttribute("cart");
            if (cart == null){
                response.getWriter().write("非法访问...");
                return;
            }
            Order order = new Order();
            BeanUtils.populate(order,request.getParameterMap());
            order.setId(UUID.randomUUID().toString());
            order.setOrderTime(new Date());
            order.setUser(user);
            //封装OrderItem
            List<OrderItem> items = new ArrayList<>();
            Double totalPrice = 0.0;
            for (Map.Entry<Product, Integer> entry : cart.entrySet()) {
                OrderItem item = new OrderItem();
                item.setProduct(entry.getKey());
                item.setBuynum(entry.getValue());
                item.setOrder(order);
                items.add(item);
                totalPrice += entry.getKey().getPrice() * entry.getValue();
            }
            order.setItems(items);
            order.setMoney(totalPrice);
            System.out.println("---------------");
            System.out.println("订单：");
            System.out.println(order);
            System.out.println("订单中的商品：");
            for (OrderItem item : items) {
                System.out.println("商品名称：" + item.getProduct().getName() + " 商品数量：" + item.getBuynum());
            }
            OrderService service = new OrderServiceImpl();
            service.createOrder(order);
            request.getSession().removeAttribute("cart");

            response.getWriter().write("购买成功...");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
