package com.web.servlet;

import com.model.Order;
import com.model.User;
import com.service.OrderService;
import com.service.impl.OrderServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/findOrderByIdServlet")
public class FindOrderByIdServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute("user");
        if (user == null){
            response.getWriter().write("非法访问...");
            return;
        }
        OrderService service = new OrderServiceImpl();
        List<Order> orders = service.findOrdersById(user.getId() + "");
        request.setAttribute("orders",orders);
        request.getRequestDispatcher("orderlist.jsp").forward(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
