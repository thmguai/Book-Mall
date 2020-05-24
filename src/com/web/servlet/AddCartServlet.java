package com.web.servlet;

import com.model.Product;
import com.service.ProductService;
import com.service.impl.ProductServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/addCartServlet")
public class AddCartServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {
            Object user = request.getSession().getAttribute("user");
            if (user != null){
                String id = request.getParameter("id");
                ProductService service = new ProductServiceImpl();
                Product product = service.findProductById(id);
                Map<Product,Integer> cart = (Map<Product, Integer>) request.getSession().getAttribute("cart");
                if (cart == null){
                    cart = new HashMap<>();
                    cart.put(product,1);
                }else {
                    if (cart.containsKey(product)){
                        cart.put(product,cart.get(product)+1);
                    }else {
                        cart.put(product,1);
                    }
                }
                /* for (Map.Entry<Product, Integer> entry : cart.entrySet()) {
                    System.out.println(entry.getKey()+"数量："+entry.getValue());
                }*/
                request.getSession().setAttribute("cart",cart);
                String a1 = "<a href=\" "+ request.getContextPath()+ "/showProductsByPageServlet" + "\">继续购物</a>";
                String a2 = "&nbsp;&nbsp;<a href=\" "+ request.getContextPath()+ "/cart.jsp" + "\">前往购物车</a>";
                response.getWriter().write(a1);
                response.getWriter().write(a2);
            }else {
                response.sendRedirect(request.getContextPath()+"/login.jsp");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
