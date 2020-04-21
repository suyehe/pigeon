package com.suye.netty;

import com.suye.consts.Protocol;
import com.suye.dto.RequestMessage;
import com.suye.dto.Session;
import com.suye.service.MessageDisruptor;
import com.suye.service.NameSpace;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.net.ssl.HandshakeCompletedEvent;

/**
 * @author radish
 * 处理websocket消息
 */
@Component
@ChannelHandler.Sharable
@Slf4j
public class TextWebSocketFrameHandler extends SimpleChannelInboundHandler<WebSocketFrame> {


    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof HandshakeCompletedEvent){
           NameSpace.connect(ctx.channel());
        }
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, WebSocketFrame msg) throws Exception {
        if (msg instanceof TextWebSocketFrame) {
            RequestMessage requestMessage = new RequestMessage();
            requestMessage.setChannel(ctx.channel());
            requestMessage.setProtocol(Protocol.WEBSOCKET);
            requestMessage.setBody(((TextWebSocketFrame) msg).text());
            MessageDisruptor.handlerMsg(requestMessage);
        }

    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        log.info("channelInactive:");
    }

}