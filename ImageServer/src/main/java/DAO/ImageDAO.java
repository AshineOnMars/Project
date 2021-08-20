package DAO;

import MyException.AppException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

//管理所有的图片
public class ImageDAO {
    /**
     * 将Image对象插入数据库
     */

    public void insert(Image image){
        //获取数据库连接
        Connection connection=DBUtil.getConnection();

        //创建并拼装SQL语句
        String sql="insert into image_table values(null,?,?,?,?,?,?)";
        PreparedStatement statement=null;
        try {
            statement=connection.prepareStatement(sql);
//            statement.setInt(1,image.getImageID());
            statement.setString(1,image.getImageName());
            statement.setInt(2,image.getSize());
            statement.setString(3,image.getUploadTime());
            statement.setString(4,image.getContentType());
            statement.setString(5,image.getPath());
            statement.setString(6,image.getMd5());
        //执行SQL语句
            int ret=statement.executeUpdate();
            if (ret!=1){
                //程序出现异常
                throw new AppException("INSERT001","插入数据库出错!");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            //关闭对象(如果放在catch前，可能程序因异常终止，但对象资源没有正确释放)
            //无论如何结束，都要正确释放资源，否则可能造成内存泄漏
            DBUtil.close(connection,statement,null);
        }

    }

    /**
     * 查找数据库中所有图片信息
     */
    public List<Image> selectAll(){
        List<Image> imageList =new ArrayList<>();
        Connection connection=DBUtil.getConnection();
        PreparedStatement statement=null;
        ResultSet resultSet=null;
        String sql="select * from image_table;";
        try {
            //执行sql语句，获取结果集
            statement=connection.prepareStatement(sql);
            resultSet=statement.executeQuery();
            //将结果集组织到List中
            while (resultSet.next()){
                Image image=new Image();
                image.setImageID(resultSet.getInt("imageID"));
                image.setImageName(resultSet.getString("imageName"));
                image.setSize(resultSet.getInt("size"));
                image.setUploadTime(resultSet.getNString("uploadTime"));
                image.setContentType(resultSet.getNString("contentType"));
                image.setContentType(resultSet.getNString("path"));
                image.setMd5(resultSet.getNString("md5"));
                imageList.add(image);
            }
            return imageList;
        } catch (Exception e) {
            throw new AppException("SELECT002","查询所有图片出错",e);
        } finally {
            //关闭资源
            DBUtil.close(connection,statement,resultSet);
        }
    }

    /**
     * 根据imageId查找指定图片信息
     */
    public Image selectOne(int imageId){

        Connection connection=DBUtil.getConnection();
        PreparedStatement statement=null;
        ResultSet resultSet=null;
        String sql="select * from image_table where imageId=?";
        int ret= 0;
        try {
            statement=connection.prepareStatement(sql);
            statement.setInt(1,imageId);
            resultSet=statement.executeQuery();

            if (resultSet.next()) {
                Image image=new Image();
                image.setImageName(resultSet.getNString("imageName"));
                image.setSize(resultSet.getInt("size"));
                image.setUploadTime(resultSet.getNString("uploadTime"));
                image.setContentType(resultSet.getNString("contentType"));
                image.setPath(resultSet.getNString("path"));
                image.setMd5(resultSet.getNString("md5"));
                return image;
            }
        } catch (Exception e) {
            throw new AppException("SELECT003","查询图片出错"+imageId,e);
        } finally {
            DBUtil.close(connection,statement,resultSet);
        }
        return null;
    }

    /**
     * 根据imageID删除指定图片
     */
    public void delete(int imageId){
        Connection connection=DBUtil.getConnection();
        PreparedStatement statement=null;
        String sql="delete from image_table where imageId=?";
        try {
            statement=connection.prepareStatement(sql);
            statement.setInt(1,imageId);
            int ret = statement.executeUpdate();
            if(ret!=1){
                throw new AppException("DELETE004","删除图片出错"+imageId);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(connection,statement,null);
        }
    }


    public Image selectByMd5(String md5) {
        Connection connection=DBUtil.getConnection();
        String sql="select * from image_table where md5=?";
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            // 3. 执行 SQL 语句
            statement = connection.prepareStatement(sql);
            statement.setString(1, md5);
            resultSet = statement.executeQuery();
            // 4. 处理结果集
            if (resultSet.next()) {
                Image image = new Image();
                image.setImageID(resultSet.getInt("imageId"));
                image.setImageName(resultSet.getString("imageName"));
                image.setSize(resultSet.getInt("size"));
                image.setUploadTime(resultSet.getString("uploadTime"));
                image.setContentType(resultSet.getString("contentType"));
                image.setPath(resultSet.getString("path"));
                image.setMd5(resultSet.getString("md5"));
                return image;
            }
        } catch (Exception e) {
            throw new AppException("MD5005","查询图片MD5出错"+md5,e);
        } finally {
            // 5. 关闭链接
            DBUtil.close(connection, statement, resultSet);
        }
        return null;
    }
}
