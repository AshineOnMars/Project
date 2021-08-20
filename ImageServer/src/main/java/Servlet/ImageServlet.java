package Servlet;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import DAO.Image;
import DAO.ImageDAO;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ImageServlet extends HttpServlet {
    /**
     * 查看图片属性: 既能查看所有, 也能查看指定
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String imageId = req.getParameter("imageId");
        if (imageId == null || imageId.equals("")) {
            // 如果id为null则，展示所有图片属性
            selectAll(req, resp);
        } else {
            // 展示指定图片
            selectOne(imageId, resp);
        }
    }

    private void selectAll(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json; charset=utf-8");
        // 1. 创建一个 ImageDao 对象, 并查找数据库
        ImageDAO imageDao = new ImageDAO();
        List<Image> images = imageDao.selectAll();
        // 2. 把查找到的结果转成 JSON 格式的字符串, 并且写回给 resp 对象
        Gson gson = new GsonBuilder().create();
        String jsonData = gson.toJson(images);
        resp.getWriter().write(jsonData);
    }

    private void selectOne(String imageId, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json; charset=utf-8");
        // 1. 创建 ImageDao 对象
        ImageDAO imageDao = new ImageDAO();
        Image image = imageDao.selectOne(Integer.parseInt(imageId));
        // 2. 使用 gson 把查到的数据转成 json 格式, 并写回给响应对象
        Gson gson = new GsonBuilder().create();
        String jsonData = gson.toJson(image);
        resp.getWriter().write(jsonData);
    }

    /**
     * 上传图片
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 1. 获取图片的属性信息, 并且存入数据库

        //  a) 需要创建一个 factory 对象 和 upload 对象,
        //   这是为了获取到图片属性做的准备工作 ---固定的逻辑
        FileItemFactory factory = new DiskFileItemFactory();
        ServletFileUpload upload = new ServletFileUpload(factory);

        // b) 通过 upload 对象进一步解析请求(解析HTTP请求中奇怪的 body 中的内容)
        //    FileItem ：代表一个上传的文件对象.
        //    理论上来说, HTTP 支持一个请求中同时上传多个文件
        List<FileItem> items = null;
        try {
            items = upload.parseRequest(req);
        } catch (FileUploadException e) {
            // 出现异常说明解析出错!
            e.printStackTrace();
            // 告诉客户端出现的具体的错误
            resp.setContentType("application/json; charset=utf-8");
            resp.getWriter().write("{ \"ok\": false, \"reason\": \"请求解析失败\" }");
            return;
        }
        //  c) 把 FileItem 中的属性提取出来, 转换成 Image 对象, 才能存到数据库中
        FileItem fileItem = items.get(0);
        Image image = new Image();
        image.setImageName(fileItem.getName());
        image.setSize((int)fileItem.getSize());

        // 手动获取当前日期, 并格式化日期, yyMMdd => 20210715
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        image.setUploadTime(simpleDateFormat.format(new Date()));

        image.setContentType(fileItem.getContentType());

        // 计算MD5
        image.setMd5(DigestUtils.md5Hex(fileItem.get()));
        image.setPath("./image/" + image.getMd5());

        // 存到数据库中
        ImageDAO imageDAO= new ImageDAO();


        //判断数据库中是否存在相同的 MD5 值的图片
        Image existImage = imageDAO.selectByMd5(image.getMd5());
        imageDAO.insert(image);
        // 2. 获取图片的内容信息, 并且写入磁盘文件
        if (existImage == null) {
            File file = new File(image.getPath());
            try {
                fileItem.write(file);
            } catch (Exception e) {
                e.printStackTrace();

                resp.setContentType("application/json; charset=utf-8");
                resp.getWriter().write("{ \"ok\": false, \"reason\": \"写磁盘失败\" }");
                return;
            }
        }

        // 3. 给客户端返回一个结果数据

        //操作成功，跳转到主页面
        resp.sendRedirect("index.html");
    }

    /**
     * 删除指定图片
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json; charset=utf-8");
        // 1. 先获取到请求中的 imageId
        String imageId = req.getParameter("imageId");
        //ImageID 不存在，报错反馈
        if (imageId == null || imageId.equals("")) {
            resp.setStatus(200);
            resp.getWriter().write("{ \"ok\": false, \"reason\": \"解析请求失败\" }");
            return;
        }
        // 2. 创建 ImageDao 对象,查看到该图片对象对应的相关属性(获取图片对应的文件路径)
        ImageDAO imageDao = new ImageDAO();
        int id=Integer.parseInt(imageId);
        Image image = imageDao.selectOne(id);

        // 请求中的ImageID 在数据库中查无此人
        if (image == null) {
            resp.setStatus(200);
            resp.getWriter().write("{ \"ok\": false, \"reason\": \"imageId 在数据库中不存在\" }");
            return;
        }
        // 3. 删除数据库中的记录
        Image existImage = imageDao.selectByMd5(image.getMd5());
        imageDao.delete(Integer.parseInt(imageId));
        // 4. 删除本地磁盘文件
        //因为数据库中多个相同文件在磁盘中只占用一个内存空间，
        // 当删除一个数据文件时，不代表其他几个相同文件也一并被删除，
        // 因此当数据库中相同文件都被删除后，才可以删除磁盘文件
        if (existImage==null) {
            File file = new File(image.getPath());
            file.delete();
        }

        //写回到响应信息
        resp.setStatus(200);
        resp.getWriter().write("{ \"ok\": true }");
    }
}

