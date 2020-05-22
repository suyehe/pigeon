package com.suye.config;

import com.alibaba.fastjson.support.spring.FastJsonRedisSerializer;
import com.suye.consts.Consts;
import com.suye.consts.Protocol;
import com.suye.dto.MessageDTO;
import com.suye.dto.Session;
import com.suye.service.MessageDisruptor;
import com.suye.service.NameSpace;
import com.suye.service.RedisRegistryServiceImpl;
import com.suye.service.RegistryService;
import com.suye.utils.NetUtil;
import com.suye.utils.SpringContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.serializer.RedisSerializer;

import java.util.concurrent.Executors;

/**
 * description
 *
 * @author zxy
 * create time 2020/3/12 11:11
 */
@Configuration
public class RedisConfig {

    @Value("${pigeon.port}")
    private Integer port;


    @Bean
    RedisMessageListenerContainer container(RedisConnectionFactory connectionFactory,
                                            MessageListenerAdapter listenerAdapter) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.addMessageListener(listenerAdapter, new PatternTopic(Consts.MESSAGE_TOPIC));
        return container;
    }


    @Bean
    StringRedisTemplate redisTemplate(RedisConnectionFactory connectionFactory) {
        return new StringRedisTemplate(connectionFactory);
    }


    @Bean
    RegistryService registryService(StringRedisTemplate redisTemplate) {
        RedisRegistryServiceImpl registryService = new RedisRegistryServiceImpl(redisTemplate, NetUtil.getLocalIp() + ":" + port, Protocol.WEBSOCKET);
        NameSpace.setRegistryService(registryService);
        return registryService;
    }

    @Bean
    MessageDisruptor disruptor(RegistryService registryService,RedisTemplate<String,MessageDTO> template) {
        return  new MessageDisruptor(Executors.newFixedThreadPool(4),registryService,template);
    }

    @Bean
    MessageListenerAdapter listenerAdapter(MessageDisruptor disruptor) {
        RedisSerializer<?> serializer = new FastJsonRedisSerializer<>(MessageDTO.class);
        MessageListenerAdapter listenerAdapter = new MessageListenerAdapter(disruptor, "onMessage");
        listenerAdapter.setSerializer(serializer);
        return listenerAdapter;
    }


    @Bean
    RedisTemplate<String, Session> sessionRedisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, Session> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);
        template.setDefaultSerializer(new FastJsonRedisSerializer<>(Session.class));
        SpringContext.regiest("sessionRedisTemplate", template);
        return template;

    }

    @Bean
    RedisTemplate<String, MessageDTO> messageBodyRedisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, MessageDTO> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);
        template.setDefaultSerializer(new FastJsonRedisSerializer<>(MessageDTO.class));
        SpringContext.regiest("messageBodyRedisTemplate", template);
        return template;

    }


}
