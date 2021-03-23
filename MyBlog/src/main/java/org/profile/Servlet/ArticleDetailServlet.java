package org.profile.Servlet;


import org.profile.dao.ArticleDAO;
import org.profile.model.Article;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


//文章列表入口
@WebServlet("/articleDetail")
public class ArticleDetailServlet extends AbstractBaseServlet{
    @Override
    protected Object process(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        //解析请求数据，获取文章id
        String id = req.getParameter("id");
        //使用ArticleDAO进行查询
        Article a = ArticleDAO.query(Integer.parseInt(id));
        return a;

    }
}
