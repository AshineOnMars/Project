package org.profile.Servlet;


import org.profile.dao.ArticleDAO;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//文章删除
@WebServlet("/articleDelete")
public class ArticleDeleteServlet extends AbstractBaseServlet{
    @Override
    protected Object process(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        //从请求体中获取文章ID
        String ids=req.getParameter("ids");
        int num= ArticleDAO.delete(ids.split(","));
        return null;
    }
}
