package com.web.servlet;

import com.exception.UserException;
import com.model.User;
import com.service.UserService;
import com.service.impl.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/loginServlet")
public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");
        UserService service = new UserServiceImpl();
        try {
            User user = service.loginUser(username, password);
            request.getSession().setAttribute("user",user);
            if ("管理员".equals(user.getRole())){
                response.sendRedirect(request.getContextPath()+"/admin/login/home.jsp");
            }else {
                //登入成功
                //request.getRequestDispatcher("/index.jsp").forward(request,response);
                //重定向解决表单重复提交
                response.sendRedirect(request.getContextPath()+"/index.jsp");

            }
        } catch (UserException e) {
            //登入失败
            e.printStackTrace();
            request.setAttribute("login_msg",e.getMessage());
            request.getRequestDispatcher("/login.jsp").forward(request,response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
