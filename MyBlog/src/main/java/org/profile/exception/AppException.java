package org.profile.exception;

/**
 * 自定义异常类：业务代码抛自定义异常或其他异常
 * 自定义异常返回给定的错误码，其他异常返回其他错误码
 *
 */
public class AppException extends RuntimeException{

    //给前端返回的(json字符串)信息，
    private String code;//报错抛异常时给定的错误码
    //传进去可看的异常信息
    public AppException(String code, String message) {
        //先调父类的构造方法
//        super(message);
//        this.code=code;
        //message来自父类RuntimeException，原本异常信息
        this(code,message,null);
    }

    //因为xxx异常导致的报错
    public AppException(String code, String message, Throwable cause) {
        super(message, cause);
        this.code=code;
    }

    public String getCode() {
        return code;
    }
}
