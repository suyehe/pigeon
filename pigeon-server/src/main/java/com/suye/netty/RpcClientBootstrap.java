package com.suye.netty;


import com.suye.consts.Protocol;
import com.suye.utils.NetUtil;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.concurrent.DefaultThreadFactory;

import java.net.URISyntaxException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


public class RpcClientBootstrap {
    private static final Map<Protocol, Bootstrap> bootstrapMap = new ConcurrentHashMap<>();

    static {


        Bootstrap wsb = new Bootstrap();
        wsb.group(new NioEventLoopGroup(4, new DefaultThreadFactory("client"))).channel(
                NioSocketChannel.class).option(
                ChannelOption.TCP_NODELAY, true).option(ChannelOption.SO_KEEPALIVE, true).option(
                ChannelOption.CONNECT_TIMEOUT_MILLIS, 1000).option(
                ChannelOption.SO_SNDBUF, 64 * 1024).option(ChannelOption.SO_RCVBUF, 64 * 1024);
        wsb.handler(
                new ChannelInitializer<SocketChannel>() {
                    @Override
                    public void initChannel(SocketChannel ch) throws URISyntaxException {
                        ChannelPipeline pipeline = ch.pipeline();
                        pipeline.addLast(new HttpClientCodec());
                        pipeline.addLast(new HttpObjectAggregator(1024 * 1024 * 10));
                        pipeline.addLast(new WebSocketClientHandler());
                    }
                });
        bootstrapMap.put(Protocol.WEBSOCKET, wsb);
        Bootstrap sb = new Bootstrap();
        sb.group(new NioEventLoopGroup(4, new DefaultThreadFactory("client"))).channel(
                NioSocketChannel.class).option(
                ChannelOption.TCP_NODELAY, true).option(ChannelOption.SO_KEEPALIVE, true).option(
                ChannelOption.CONNECT_TIMEOUT_MILLIS, 1000).option(
                ChannelOption.SO_SNDBUF, 64 * 1024).option(ChannelOption.SO_RCVBUF, 64 * 1024);
        sb.handler(
                new ChannelInitializer<SocketChannel>() {
                    @Override
                    public void initChannel(SocketChannel ch) throws URISyntaxException {
                        ChannelPipeline pipeline = ch.pipeline();
                        pipeline.addLast(new IdleStateHandler(10, 10, 10))
                                .addLast(new ObjectDecoder(ClassResolvers.softCachingResolver(this.getClass().getClassLoader())))
                                .addLast(new ObjectEncoder());
                        pipeline.addLast(new HttpClientCodec());
                    }
                });

        bootstrapMap.put(Protocol.SELF, sb);


    }


    public static SocketChannel getchannel(String address, Protocol protocol) {
        return (SocketChannel) bootstrapMap.get(protocol).connect(NetUtil.toInetSocketAddress(address)).channel();
    }


}
