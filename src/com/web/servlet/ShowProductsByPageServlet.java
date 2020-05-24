package com.web.servlet;

import com.model.PageResult;
import com.model.Product;
import com.service.ProductService;
import com.service.impl.ProductServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/showProductsByPageServlet")
public class ShowProductsByPageServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String category = request.getParameter("category");
        String pageStr = request.getParameter("page");
        int page = 1;
        if (pageStr != null && !"".equals(pageStr)){
            page = Integer.parseInt(pageStr);
        }
        ProductService service = new ProductServiceImpl();
        PageResult<Product> pr = service.findBooks(category, page);
        request.setAttribute("pr",pr);
        request.setAttribute("category",category);
        request.getRequestDispatcher("/product_list.jsp").forward(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
