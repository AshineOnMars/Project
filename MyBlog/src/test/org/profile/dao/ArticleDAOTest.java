package org.profile.dao;

import org.junit.Test;
import org.profile.model.Article;

import java.util.ArrayList;
import java.util.List;

public class ArticleDAOTest {

    @Test
    public void queryByUserId() {
        ArticleDAO articleDAO=new ArticleDAO();
//        Article article=new Article();

        List<Article> list=new ArrayList<>();
//        传入用户ID，根据用户ID查询他的文章信息
        list=articleDAO.queryByUserId(1);
        System.out.println(list.size());
//        遍历打印文章标题
        for (Article article : list) {
            System.out.println(article.getTitle());
        }

    }

//    从界面删除：bug！
    @Test
    public void delete() {
        ArticleDAO articleDAO=new ArticleDAO();
//        num：表示删除的条数
        int num =articleDAO.delete(new String[]{"23","24","25"});
        System.out.println(num);

    }

    @Test
    public void insert() {
        ArticleDAO articleDAO=new ArticleDAO();
        Article article=new Article();
        article.setId(8);
        article.setUserId(1);
        article.setTitle("测试之美没没没没");
        article.setContent("皮卡皮卡");
        articleDAO.insert(article);

    }

    @Test
    public void query() {
        ArticleDAO articleDAO=new ArticleDAO();
        Article a1=articleDAO.query(666);
        Article a2=articleDAO.query(8);
        Article a3=articleDAO.query(1);
        Article a4=articleDAO.query(15);
//        System.out.println("a1: "+a1.getTitle());
//        System.out.println("a2: "+a2.getTitle());
        System.out.println("a3: "+a3);
        System.out.println("a4: "+a4.getTitle());

    }

    @Test
    public void update() {
        ArticleDAO articleDAO=new ArticleDAO();
        Article article=new Article();
//        article.setId(26);
//        article.setTitle("子丑寅卯");//子丑寅卯
//        article.setContent("申戌酉亥");
//        article.setUserId(2);
        article.setId(15);
//        article.setUserId(2);  //userId不可更改
        article.setTitle("辰巳午未申");//子丑寅卯
        article.setContent("子丑寅卯");//申戌酉亥
        int update = articleDAO.update(article);
        System.out.println(update);
    }
}