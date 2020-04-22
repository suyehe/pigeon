package com.suye.service;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.suye.dto.Session;
import com.suye.netty.RpcClientBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFutureListener;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * description manage channel include remote server chanel and  client channel
 *
 * @author zxy
 * create time 2020/3/13 15:15
 */
@RequiredArgsConstructor
public class AppNamespace {


    private final String app;

    private final List<Session> sessions=Lists.newArrayList();


    public void groupSession(Session session) {
        sessions.add(session);
    }

    public void unConnect(Session session) {
        sessions.remove(session);
    }
}
