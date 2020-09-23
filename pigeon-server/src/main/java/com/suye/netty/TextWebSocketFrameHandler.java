package com.suye.netty;

import com.suye.service.Disruptor;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author radish
 * 处理websocket消息
 */
@ChannelHandler.Sharable
@Slf4j
@RequiredArgsConstructor
public class TextWebSocketFrameHandler extends SimpleChannelInboundHandler<WebSocketFrame> {

    private final Disruptor disruptor;



    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof WebSocketServerProtocolHandler.HandshakeComplete) {

        }
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, WebSocketFrame msg) throws Exception {
        if (msg instanceof TextWebSocketFrame) {
            disruptor.disruptor(((TextWebSocketFrame) msg).text(),ctx.channel());
        }

    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.error("channel error:{}",cause);
    }
}