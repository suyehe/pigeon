package com.suye.netty;


import com.google.common.collect.Maps;
import com.suye.config.SpringContext;
import com.suye.dto.Session;
import com.suye.service.NameSpace;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpMethod;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Map;
import java.util.Objects;


/**
 * description
 *
 * @author zxy
 * create time 2020/3/16 15:15
 */
@ChannelHandler.Sharable
public class DispatcherHandler extends SimpleChannelInboundHandler<FullHttpRequest> {

    private RedisTemplate<String,Session> redisTemplate=SpringContext.getBean("sessionRedisTemplate");

    private static final String split_string="[?]";


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest msg) throws Exception {
         if (msg.method().equals(HttpMethod.GET)){
            if (msg.uri().contains("token")){
                Map<String, String> reqMap = getReqMap(msg);
                String token = reqMap.get("token");
                Session session = redisTemplate.opsForValue().get(token);
                if (Objects.nonNull(session)){
                    NameSpace.bindChannelSession(ctx.channel(),session);
                    ctx.fireChannelRead(msg.retain());
                }
            }
         }

    }

    private static Map<String, String> getReqMap(FullHttpRequest msg) {
        String[] split = msg.uri().split(split_string);
        msg.setUri(split[0]);
        String[] params = split[1].split("&");
        Map<String,String> reqMap=Maps.newHashMap();
        for (int i = 0; i < params.length; i++) {
            String[] keyValue = params[i].split("=");
            reqMap.put(keyValue[0],keyValue[1]);
        }
        return reqMap;
    }


}
