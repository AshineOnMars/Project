package DAO;

import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBUtilTest {
    @Test
    public void getConnection() {
        Connection connection =DBUtil.getConnection();
        if (connection==null){
            System.out.println("数据库连接失败");
        }else {
            System.out.println("数据库连接成功");
        }
    }


    @Test
    public void close(){
        Connection connection=DBUtil.getConnection();
        String sql="select imageName from image_table where imageId=30";
        PreparedStatement statement=null;
        ResultSet resultSet=null;
        try {
            statement= connection.prepareStatement(sql);
            resultSet=statement.executeQuery();
            System.out.println(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(connection,statement,resultSet);
        }
    }

}