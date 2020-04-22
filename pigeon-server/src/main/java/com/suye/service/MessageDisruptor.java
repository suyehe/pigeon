package com.suye.service;

import com.alibaba.fastjson.JSON;
import com.suye.consts.Consts;
import com.suye.dto.MessageDTO;
import com.suye.dto.RequestMessage;
import com.suye.dto.Response;
import com.suye.dto.Session;
import io.netty.channel.Channel;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

/**
 * description message async
 *
 * @author zxy
 * create time 2020/3/13 15:15
 */

@Slf4j
@AllArgsConstructor
public class MessageDisruptor {

    private final ExecutorService executor;

    private final RegistryService registryService;

    private final RedisTemplate<String, MessageDTO> redisTemplate;


    public Future handlerMsg(RequestMessage msg) {
        return executor.submit(() -> {
            log.debug("consume msg :{}", msg);
            Session session = NameSpace.getChannelSession(msg.getChannel());
            MessageDTO body = JSON.parseObject((String) msg.getBody(), MessageDTO.class);
            body.setFrom(session.getUsername());
            try {
                redisTemplate.convertAndSend(Consts.MESSAGE_TOPIC, body);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return writeAndFlush(msg.getChannel(), Response.success(body.getId()));
        });
    }


    public void onMessage(MessageDTO message) {
        log.debug("msgDel:{}", message);
        Long to = message.getTo();
        writeAndFlush(NameSpace.getChannel(to), message);
    }


    public Future writeAndFlush(Channel channel, Object o) {
        return channel.writeAndFlush(new TextWebSocketFrame(o.toString()));
    }
}
