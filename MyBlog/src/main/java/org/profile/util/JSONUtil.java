package org.profile.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;

//序列化/反序列化
public class JSONUtil {
    private static final ObjectMapper MAPPER = new ObjectMapper();

    /**
     * 序列化操作
     * @param o 序列化类型
     * @return 序列化对象
     *
     */
    public static String serialize(Object o){
        try {
            //将对象序列化成json字符串
            return MAPPER.writeValueAsString(o);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new RuntimeException("json序列化失败："+o);
        }
    }

    /**
     * 反序列化操作：将JSON字符串反序列化为article对象
     * @param is ：输入流读取的json字符串
     * @param clazz：指定反序列化的的类型
     * @param <T>：泛型
     * @return 反序列化的对象
     */
    public static <T> T deserialize(InputStream is, Class<T> clazz){
        try {
            //反序列化为Java对象
            return MAPPER.readValue(is, clazz);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("json反序列化失败："+clazz.getName());
        }
    }
}
