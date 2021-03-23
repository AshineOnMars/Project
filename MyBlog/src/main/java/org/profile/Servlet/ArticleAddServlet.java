package org.profile.Servlet;


import org.profile.dao.ArticleDAO;
import org.profile.model.Article;
import org.profile.model.User;
import org.profile.util.JSONUtil;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.InputStream;

//新建文章
@WebServlet("/articleAdd")
public class ArticleAddServlet extends AbstractBaseServlet{
    @Override
    protected Object process(HttpServletRequest req, HttpServletResponse resp) throws Exception {

        //通过session获取用户id
        HttpSession session=req.getSession(false);
        User user=(User) session.getAttribute("user");
        //请求数据类型是Application.json

        //通过输入流从请求体中完整的获取json格式字符串，将其反序列化为article对象

        //1.从请求体中完整的获取内容
        InputStream is=req.getInputStream();
        //2.反序列化
        Article a= JSONUtil.deserialize(is,Article.class);
        //3.为article类型的对象a设置用户ID
        a.setUserId(user.getId());
        //调用ArticleDAO.insert() 进行插入
        int num= ArticleDAO.insert(a);
        return null;
    }
}
