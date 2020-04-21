package com.suye.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.suye.consts.Consts;
import com.suye.dto.MessageBody;
import com.suye.dto.RequestMessage;
import com.suye.dto.Response;
import com.suye.dto.Session;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.RedisTemplate;


import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

/**
 * description message async
 *
 * @author zxy
 * create time 2020/3/13 15:15
 */

@Slf4j
public class MessageDisruptor   {
    @Setter
    private  static ExecutorService executor;
    @Setter
    private  static  RedisTemplate <String,MessageBody>redisTemplate;



    public static Future handlerMsg(RequestMessage msg) {
        return executor.submit(() -> {
            log.debug("consume msg :{}", msg);
            Session session = NameSpace.getChannelSession(msg.getChannel());
            MessageBody body =JSON.parseObject((String) msg.getBody(),MessageBody.class);
            body.setFrom(session.getUsername());
            try {
                redisTemplate.convertAndSend(Consts.MESSAGE_TOPIC,body);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return writeAndFlush(msg.getChannel(),Response.success(body.getId()));
        });
    }






    public void onMessage(MessageBody message) {
        log.debug("msgDel:{}",message);
        writeAndFlush(NameSpace.getChannel(message.getTo()),message);
    }



    public static Future writeAndFlush(Channel channel, Object o){
        return channel.writeAndFlush(new TextWebSocketFrame(o.toString()));
    }
}
