package com.suye.netty;


import com.google.common.collect.Maps;
import com.suye.consts.Consts;
import com.suye.dao.LineUser;
import com.suye.service.NameSpace;
import com.suye.service.UserService;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpMethod;
import lombok.RequiredArgsConstructor;

import java.util.Map;



/**
 * description
 *
 * @author zxy
 * create time 2020/3/16 15:15
 */
@ChannelHandler.Sharable
@RequiredArgsConstructor
public class PreValidHandler extends SimpleChannelInboundHandler<FullHttpRequest> {


    private static final String split_string="[?]";

    private final NameSpace nameSpace;

    private  final UserService userService;



    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest msg) throws Exception {
         if (msg.method().equals(HttpMethod.GET)){
            if (msg.uri().contains(Consts.token)){
                Map<String, String> reqMap = getReqMap(msg);
                String token = reqMap.get(Consts.token);
                ctx.channel().closeFuture().addListener((ChannelFutureListener)nameSpace::offline);
                LineUser lineUser = userService.tokenUser(token);
                nameSpace.userOnline(lineUser.toSession(ctx.channel()),ctx.channel());
                ctx.fireChannelRead(msg.retain());
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
