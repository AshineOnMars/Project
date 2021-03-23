package org.profile.Servlet;


import org.profile.exception.AppException;
import org.profile.model.JSONResponse;
import org.profile.util.JSONUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

//模板设计模式：将公共部分抽象出来，剩余部分留给子类具体实现
//将所有接口都按照统一方式进行处理
public abstract class AbstractBaseServlet extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest req,
                         HttpServletResponse resp) throws ServletException,IOException {
        doPost(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req,
                          HttpServletResponse resp) throws ServletException,IOException {

        //设置请求体的编码格式
        req.setCharacterEncoding("UTF-8");
        //设置响应体
        resp.setCharacterEncoding("UTF-8");
        //设置响应体数据类型（浏览器采取什么方式执行)
        resp.setContentType("application/json");

        //TODO
        JSONResponse json=new JSONResponse();
        try {
            //调用子类重写方法
            Object data=process(req, resp);
            //子类的process执行完成没有抛异常，表示业务执行成功
            json.setSuccess(true);
            json.setData(data);

            //对所有异常统一catch
        } catch (Exception e) {
            //异常处理:自定义异常

            //先将原有异常信息打印
            e.printStackTrace();
            //json.success()不需要设置了，new的时候就是了

            //将错误码和未知错误进行绑定
            String code="UNKNOWN";
            String s="未知错误";

            if(e instanceof AppException){
                //获取异常信息
                code=((AppException) e).getCode();
                s=e.getMessage();
            }
            //写入json中
            json.setCode(code);
            json.setMessage(s);
        }
        //打印
        PrintWriter pw=resp.getWriter();
        //序列化成json格式后打印输出
        pw.println(JSONUtil.serialize(json));
        pw.flush();
        pw.close();
    }

    //返回数据类型与data同,子类重写的方法
    protected abstract Object process(HttpServletRequest req,
                                      HttpServletResponse resp) throws Exception;

}



