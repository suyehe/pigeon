package com.suye.service;

import com.suye.consts.Protocol;
import com.suye.dto.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * description
 *
 * @author zxy
 * create time 2020/4/21 14:14
 */
@Service
public class SessionService {

    @Autowired
    private RedisTemplate<String,Session> sessionRedisTemplate;

    private RegistryService redisRegistryService=RedisRegistryServiceImpl.getRegistryService();



    public Session generate(Long id, String app, Protocol protocol){
        Session session = new Session();
        session.setUserId(id);
        session.setAppId(app);
        session.setProtocol(protocol);
        session.setServerAddr(redisRegistryService.lookProtocolServer(protocol));
        session.setSessionId(UUID.randomUUID().toString());
        session.setToken(UUID.randomUUID().toString());
        sessionRedisTemplate.opsForValue().set(session.getToken(),session);
        return session;


    }


}
