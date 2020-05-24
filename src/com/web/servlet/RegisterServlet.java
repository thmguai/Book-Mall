package com.web.servlet;

import com.exception.UserException;
import com.model.User;
import com.service.UserService;
import com.service.impl.UserServiceImpl;
import org.apache.commons.beanutils.BeanMap;
import org.apache.commons.beanutils.BeanUtils;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.UUID;

@WebServlet("/registerServlet")
public class RegisterServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //校验验证码
        String checkCode = request.getParameter("checkCode");
        String checkcode_session = (String) request.getSession().getAttribute("checkcode_session");

        if (!checkCode.equals(checkcode_session)){
            request.setAttribute("checkcode_err","验证码错误");
            request.getRequestDispatcher("/register.jsp").forward(request,response);
            return;
        }
        User user = new User();
        try {
            BeanUtils.populate(user,request.getParameterMap());
            user.setActiveCode(UUID.randomUUID().toString());
            user.setRole("普通用户");
            user.setRegistTime(new Date());
            System.out.println(user);
            UserService service = new UserServiceImpl();
            service.addUser(user);

            request.getRequestDispatcher("/registersuccess.jsp").forward(request,response);
        } catch (UserException e){
            e.printStackTrace();
            request.setAttribute("register_err",e.getMessage());
            request.getRequestDispatcher("/register.jsp").forward(request,response);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
