package com.suye.netty;

import com.suye.consts.Consts;
import com.suye.consts.Protocol;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import io.netty.handler.stream.ChunkedWriteHandler;
import io.netty.util.concurrent.DefaultThreadFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.InetSocketAddress;
import java.util.Objects;


/**
 * description
 *
 * @author zxy
 * create time 2019/10/25 17:17
 */
@Slf4j
@Component
public class NettyServer {
    private int port = 8888;

    private Protocol protocol = Protocol.WEBSOCKET;

    @Autowired
    private TextWebSocketFrameHandler webSocketFrameHandler;

    public void start() {
        ServerBootstrap bootstrap = new ServerBootstrap();
        EventLoopGroup parentGroup = new NioEventLoopGroup(1, new DefaultThreadFactory("parentGroup"));
        EventLoopGroup workGroup = new NioEventLoopGroup(4, new DefaultThreadFactory("workGroup"));
        bootstrap.group(parentGroup, workGroup);
        bootstrap.channel(NioServerSocketChannel.class);

        bootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel ch) throws Exception {
                if (Objects.equals(Protocol.WEBSOCKET, protocol)) {
                    ch.pipeline().addLast(new HttpServerCodec());
                    ch.pipeline().addLast(new HttpObjectAggregator(64 * 1024));
                    ch.pipeline().addLast(Consts.DISPACHER, new DispatcherHandler());
                    ch.pipeline().addLast(new ChunkedWriteHandler());
                    ch.pipeline().addLast(new WebSocketServerProtocolHandler("/ws"));
                    ch.pipeline().addLast(webSocketFrameHandler);
                }
                if (Objects.equals(Protocol.SELF, protocol)) {
                    ch.pipeline().addLast(new ObjectDecoder(ClassResolvers.softCachingResolver(String.class.getClassLoader())));
                    ch.pipeline().addLast(new ObjectEncoder());
                    ch.pipeline().addLast(new DispatcherHandler());
                }


            }
        });
        bootstrap.childOption(ChannelOption.SO_KEEPALIVE, true);
        ChannelFuture future = bootstrap.bind(new InetSocketAddress(port));
        future.awaitUninterruptibly();
        log.info("netty server start at port:{}", port);
    }
}
