package com.suye.service;

import com.google.common.collect.Maps;
import com.suye.dto.Session;
import com.suye.netty.RpcClientBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;


import java.util.Collection;
import java.util.Map;

import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * channel session manage
 *
 * @author zxy
 * create time 2020/4/9 14:14
 */
@Slf4j
public class NameSpace {
    @Setter
    private static  RegistryService registryService;


    private static final Map<String,AppNamespace> namespace=Maps.newConcurrentMap();

    private static final Map<String, Channel> REMOTE_CHANNELS = Maps.newConcurrentMap();

    private static final Map<Channel,Session> CHANNEL_SESSION=Maps.newConcurrentMap();

    private static ExecutorService connect = Executors.newSingleThreadExecutor();

    private static final ChannelFutureListener remover = NameSpace::close;



    public static AppNamespace getAppNamespace(String app){
        return namespace.putIfAbsent(app,new AppNamespace(app));
    }

    public static Session bindChannelSession(Channel channel, Session session){
        return CHANNEL_SESSION.putIfAbsent(channel,session);
    }

    public static void connect(Channel channel){
        connect.submit(()->{
            channel.closeFuture().addListener(remover);
            Session session = getChannelSession(channel);
            session.setActive(true);
            getAppNamespace(session.getAppId()).groupSession(session);
            log.debug("session:{} connect",session);
            registryService.registerSession(session);
        });
    }

    public static Collection<Session> all(){
        return CHANNEL_SESSION.values();
    }



    public static Session getChannelSession(Channel channel){
        return CHANNEL_SESSION.get(channel);
    }

    public static Session getSession(Channel channel){
        return CHANNEL_SESSION.get(channel);
    }


    public static Channel remoteChannel(String sessionId) {
        return REMOTE_CHANNELS.computeIfAbsent(registryService.lookUpSessionServer(sessionId), (addr)-> RpcClientBootstrap.getchannel(addr,registryService.lookUpServerProtocol(addr)));
    }



    public static Channel getChannel(Long userId) {
        for (Map.Entry<Channel, Session> entry : CHANNEL_SESSION.entrySet()) {
            if (Objects.equals(entry.getValue().getUserId(), userId)) {
                return entry.getKey();
            }
        }
        return null;
    }



    private static void close(ChannelFuture future) {
        Session session = CHANNEL_SESSION.remove(future.channel());
        namespace.get(session.getAppId()).unConnect(session);
        registryService.unRegisterSession(session);
        future.channel().closeFuture().removeListener(remover);
    }
}
