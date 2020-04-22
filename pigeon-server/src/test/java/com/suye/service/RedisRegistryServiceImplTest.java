package com.suye.service;

import com.suye.consts.Protocol;
import com.suye.dto.Session;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.UUID;


@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class RedisRegistryServiceImplTest {
    @Autowired
    private  RegistryService registryService;

    @Test
    public void registerSession() {
        Session session = new Session();
        session.setProtocol(Protocol.WEBSOCKET);
        session.setActive(true);
        session.setToken(UUID.randomUUID().toString());
        session.setUserId(10086);
        session.setSessionId(9999 + "");
        session.setUsername("zxy");
        registryService.registerSession(session);
    }

    @Test
    public void unRegisterSession() {

    }

    @Test
    public void lookUpSessionServer() {
        String server = registryService.lookUpSessionServer(10086L);
        assert server.equals(registryService.getLocalServerAddr());
    }

    @Test
    public void lookUpServerProtocol() {
        Protocol protocol = registryService.lookUpServerProtocol(registryService.getLocalServerAddr());
        assert protocol.equals(registryService.getLocalServerPro());
    }


    @Test
    public void lookProtocolServer() {
        String server = registryService.lookProtocolServer(Protocol.WEBSOCKET);
        assert server.equals(registryService.getLocalServerAddr());
    }


}