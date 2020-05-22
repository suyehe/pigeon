package com.suye.dto;

import com.alibaba.fastjson.JSON;
import lombok.Data;

/**
 * description
 *
 * @author zxy
 * create time 2020/4/20 17:17
 */
@Data
public class Response<T> {
    private int code;
    private String errorMsg;
    private T result;

   public static <T>Response<T> success(T t){
       Response<T> response = new Response<>();
       response.setResult(t);
       response.setCode(200);
       return response;
   }

    public static  Response success(){
        Response response = new Response();
        response.setCode(200);
        return response;
    }


    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
