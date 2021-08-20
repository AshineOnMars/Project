package MyException;

public class AppException extends RuntimeException {
    //给前端返回的(json字符串)信息，保存错误码，
    private String code;

    public AppException(String code, String message) {
        this(code,message,null);

    }

    //因为xxx异常导致的报错
    public AppException(String code, String message, Throwable cause) {
        super(message, cause);
        this.code=code;
    }

    //后期需要获取错误码使用
    public String getCode() {
        return code;
    }
}
