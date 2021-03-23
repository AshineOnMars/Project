package org.profile.dao;


import org.profile.exception.AppException;
import org.profile.model.User;
import org.profile.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Date;

public class LoginDAO {

    public static User query(String username) {
        Connection c=null;
        PreparedStatement ps=null;
        ResultSet rs=null;
        User user=null;

        try {
            c= DBUtil.getConnection();
            String sql="select id, username, password, " +
                    "nickname, sex, birthday, head from user " +
                    "where username=?";
            ps=c.prepareStatement(sql);
            //设置占位符
            ps.setString(1,username);
            rs=ps.executeQuery();
            while (rs.next()){
                user=new User();
                //设置user的值
                user.setId(rs.getInt("id"));
                user.setUsername(username);
                user.setPassword(rs.getString("password"));
                user.setNickname(rs.getString("nickname"));
                user.setSex(rs.getBoolean("sex"));
                //创建日期
                Date birthday=rs.getDate("birthday");
                if(birthday!=null) {
                    user.setBirthday(new Date(birthday.getTime()));
                }
                user.setHead(rs.getString("head"));
            }
            return user;
        } catch (Exception e) {
            //查后台堆栈报错信息
            throw new AppException("LOG001","查询用户操作出错",e);
        } finally {
            DBUtil.close(c,ps,rs);
        }

    }
}
