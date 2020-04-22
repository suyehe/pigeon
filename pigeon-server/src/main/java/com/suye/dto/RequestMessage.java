package com.suye.dto;


import com.suye.consts.Protocol;
import io.netty.channel.Channel;
import lombok.Data;




/**
 * description
 *
 * @author zxy
 * create time 2020/3/13 15:15
 */
@Data
public class RequestMessage {
    private Channel channel;
    private Protocol protocol;
    private String app;
    private Object body;

}
