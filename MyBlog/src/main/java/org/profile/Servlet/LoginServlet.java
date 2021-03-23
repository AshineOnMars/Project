package org.profile.Servlet;


import org.profile.dao.LoginDAO;
import org.profile.exception.AppException;
import org.profile.model.User;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

//登录后端，验证登录准确性，及设置session
@WebServlet("/login")
public class LoginServlet extends AbstractBaseServlet {
    @Override
    protected Object process(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        //获取请求数据
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        //先查询出一个用户，映射
        User user= LoginDAO.query(username);
        //用户名为空
        if(user==null){
            throw new AppException("Log002","用户不存在");
        }
        //密码匹配不到
        // 有可能空指针异常，不能反
        if(!user.getPassword().equals(password)){
            throw new AppException("Log003","用户名或密码错误");
        }
        //会话管理
        //用户名、密码正确，种session
        HttpSession session=req.getSession();
        session.setAttribute("user",user);
        return null;
    }
}
