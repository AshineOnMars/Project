package org.profile.Servlet;

import org.junit.Test;
import org.profile.dao.LoginDAO;
import org.profile.exception.AppException;
import org.profile.model.User;

public class LoginServletTest {

    @Test
    public void process() {
//        LoginServlet loginServlet=new LoginServlet();

        LoginDAO loginDAO=new LoginDAO();
        String user="唐三藏";
        String pass="1";
        User u= loginDAO.query(user);
        if (u==null){
            throw new AppException("Log002","用户不存在");
        }else if(!u.getPassword().equals(pass)){
            throw new AppException("Log003","用户名或密码错误");
        }else {
            System.out.println("登陆成功");
        }
    }
}