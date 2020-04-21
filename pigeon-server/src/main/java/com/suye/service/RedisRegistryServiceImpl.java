package com.suye.service;


import com.suye.consts.Protocol;
import com.suye.dto.Session;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.Map;
import java.util.Objects;

/**
 * description
 *
 * @author zxy
 * create time 2020/3/11 18:18
 */
@RequiredArgsConstructor
@Slf4j
public class RedisRegistryServiceImpl implements RegistryService {

    private final RedisTemplate<String, String> redisTemplate;
    private final String serverAddr;
    private final Protocol protocol;
    @Setter
    @Getter
    private static RegistryService registryService;

    private static final String session_local_key="session_local_key";


    @SneakyThrows
    @PostConstruct
    public void register() {
        log.info("register serverAddr:{}", serverAddr);
        redisTemplate.opsForHash().put(getRedisRegistryKey(), serverAddr, protocol.name());
    }


    @SneakyThrows
    @PreDestroy
    public void unregister() {
        log.info("unregister serverAddr:{}", serverAddr);
        redisTemplate.opsForHash().delete(getRedisRegistryKey(), serverAddr);
    }


    private String getRedisRegistryKey() {
        return "redis_registry";
    }


    @Override
    public void registerSession(Session session) {
        redisTemplate.opsForSet().add(serverAddr,session.getSessionId());
        redisTemplate.opsForHash().put(session_local_key, session.getSessionId(), serverAddr);
    }

    @Override
    public void unRegisterSession(Session session) {
        redisTemplate.opsForSet().remove(serverAddr, session.getSessionId());
        redisTemplate.opsForHash().delete(session_local_key, session.getSessionId());
    }


    @Override
    public String lookUpSessionServer(String sessionId) {
        return (String) redisTemplate.opsForHash().get(session_local_key, sessionId);
    }


    @Override
    public Protocol lookUpServerProtocol(String addr) {
        return Protocol.valueOf((String) redisTemplate.opsForHash().get(getRedisRegistryKey(), addr));
    }

  @Override
  public String getLocalServerAddr(){
        return serverAddr;
  }

    @Override
    public Protocol getLocalServerPro() {
        return protocol;
    }


    @Override
    public String lookProtocolServer(Protocol protocol){
        Map<Object, Object> entries = redisTemplate.opsForHash().entries(getRedisRegistryKey());
        for (Map.Entry<Object, Object> entry : entries.entrySet()) {
           if (Objects.equals(protocol.name(),entry.getValue())){
               return (String) entry.getKey();
            }
        }
        return null;
    }


}
