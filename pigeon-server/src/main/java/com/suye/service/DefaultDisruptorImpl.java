package com.suye.service;

import com.alibaba.fastjson.JSON;
import com.suye.dao.LineSession;
import com.suye.dto.Message;
import com.suye.dto.Response;
import io.netty.channel.Channel;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * description
 *
 * @author zxy
 * create time 2020/9/16 15:15
 */
@Slf4j
@RequiredArgsConstructor
public class DefaultDisruptorImpl implements Disruptor {
    private final ExecutorService service = Executors.newFixedThreadPool(16);

    private final NameSpace nameSpace;

    private ToTransfer toTransfer;



    @Override
    public void disruptor(Object msg, Channel channel) {
        service.submit(() -> {
            Message message = null;
            try {
                if (msg instanceof String) {
                    message = JSON.parseObject(msg.toString(), Message.class);
                }
                LineSession user = nameSpace.user(channel);
                log.info("user say:{} msg:{}", user, message);
                to(channel, message);
            } catch (Exception e) {
                log.error(":{}", e);
            }

        });
    }

    private void to(Channel channel, Message message) {
        String to = message.getTo();
        List<LineSession> userIds = toTransfer.userId(to);
        for (LineSession userId : userIds) {
            nameSpace.userChannel(userId)
                    .writeAndFlush( new TextWebSocketFrame(message.getContent()) );
            channel.writeAndFlush(new TextWebSocketFrame(Response.success().toString()));
        }

    }

}
