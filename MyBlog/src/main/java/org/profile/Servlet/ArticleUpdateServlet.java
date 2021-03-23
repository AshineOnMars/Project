package org.profile.Servlet;


import org.profile.dao.ArticleDAO;
import org.profile.model.Article;
import org.profile.util.JSONUtil;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;

//文章更新
@WebServlet("/articleUpdate")
public class ArticleUpdateServlet extends AbstractBaseServlet{
    @Override
    protected Object process(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        //从请求体中获取请求体的数据---JSON字符串
        InputStream is=req.getInputStream();
        //将json字符串反序列化为article的对象
        //这里是在原文章基础上修改，用户ID不用再设置
        Article a= JSONUtil.deserialize(is,Article.class);
        int num= ArticleDAO.update(a);
        return null;
    }
}
