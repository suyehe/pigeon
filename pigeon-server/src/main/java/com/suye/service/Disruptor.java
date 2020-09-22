package com.suye.service;


import io.netty.channel.Channel;

/**
 * description
 *
 * @author zxy
 * create time 2020/9/16 15:15
 */
public interface Disruptor {

    void disruptor(Object msg, Channel channel);
}
