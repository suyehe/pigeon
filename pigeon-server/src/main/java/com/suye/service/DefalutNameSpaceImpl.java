package com.suye.service;

import com.google.common.collect.Maps;
import com.suye.dao.LineSession;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

/**
 * description
 *
 * @author zxy
 * create time 2020/9/16 14:14
 */
@Slf4j
public class DefalutNameSpaceImpl implements NameSpace {
    private static final Map<LineSession, Channel> USER_CHANNEL = Maps.newConcurrentMap();
    private static final Map<Channel, LineSession> CHANNEL_USER = Maps.newConcurrentMap();

    @Override
    public Channel userChannel(LineSession session) {
        return USER_CHANNEL.get(session);
    }


    @Override
    public boolean userOnline(LineSession session) {
        return USER_CHANNEL.containsKey(session);
    }

    @Override
    public void userOnline(LineSession session, Channel channel){
        USER_CHANNEL.put(session,channel);
        CHANNEL_USER.put(channel, session);
        channel.closeFuture().addListener((ChannelFutureListener)this::offline);
    }



    @Override
    public LineSession user(Channel channel) {
        return CHANNEL_USER.get(channel);
    }

    @Override
    public void offline(ChannelFuture future) {
        Channel channel = future.channel();
        LineSession session = CHANNEL_USER.remove(channel);
        if (Objects.nonNull(session)){
            USER_CHANNEL.remove(session);
            log.info("user offline user:{}",session);
        }

    }

   @Override
   public Set<LineSession> onlie(){
        return USER_CHANNEL.keySet();
    }
}
