package com.suye.service;

import com.suye.dao.LineSession;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;

import java.util.Set;


/**
 * description
 *
 * @author zxy
 * create time 2020/9/16 14:14
 */
public interface NameSpace {


    Channel userChannel(LineSession session);

    boolean userOnline(LineSession session);

    void userOnline(LineSession session, Channel channel);

    LineSession user(Channel channel);

    void offline(ChannelFuture future);

    Set<LineSession> onlie();
}
