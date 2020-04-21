package com.suye.dto;

import com.alibaba.fastjson.JSON;
import lombok.Data;

/**
 * description
 *
 * @author zxy
 * create time 2020/3/13 15:15
 */
@Data
public class MessageBody {
    private Long id;
    private String from;
    private String to;
    private long sendTime;
    private String msg;
    private int type;
    private int status;


    public static void main(String[] args) {
        MessageBody body = new MessageBody();
        body.setId(10L);
        body.setSendTime(System.currentTimeMillis());
        body.setTo("zxy");
        body.setMsg("hello "+body.getTo());
        System.out.println(JSON.toJSONString(body));
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
