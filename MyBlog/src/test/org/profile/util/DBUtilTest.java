package org.profile.util;

import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DBUtilTest {
    @Test
    public void getConnection() {
        DBUtil db=new DBUtil();
        Connection c=db.getConnection();
        if (c!=null){
            System.out.println("连接成功");
        }else {
            System.out.println("连接失败");
        }
    }

    @Test
    public void close() throws SQLException {
        DBUtil db=new DBUtil();
        Connection c=db.getConnection();
        String  sql="select id,title from article where user_id=1";
        PreparedStatement s= (PreparedStatement) c.prepareStatement(sql);
        db.close(c,s,null);
    }

    @Test
    public void testClose() {
        DBUtil db=new DBUtil();
        Connection c=db.getConnection();

    }
}