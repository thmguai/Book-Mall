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
import java.util.Map;
import java.util.Set;

/**
 * 更改购物车商品数量
 */
@WebServlet("/changeNumServlet")
public class ChangeNumServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {
            String id = request.getParameter("id");
            String num = request.getParameter("num");
            ProductService service = new ProductServiceImpl();
            Product product = service.findProductById(id);
            if (Integer.parseInt(num) <= product.getPnum()){

                Map<Product,Integer> cart = (Map<Product, Integer>) request.getSession().getAttribute("cart");
                if (cart.containsKey(product)){
                    if ("0".equals(num)){
                        cart.remove(product);
                    }else {
                        cart.put(product,Integer.parseInt(num));
                    }
                }
                request.getSession().setAttribute("cart",cart);
                response.sendRedirect(request.getContextPath()+"/cart.jsp");
            }else {
                response.sendRedirect(request.getContextPath()+"/cart.jsp");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
