package com.suye.config;

import com.suye.service.CacheUserServiceImpl;
import com.suye.service.DefalutNameSpaceImpl;
import com.suye.service.NameSpace;
import com.suye.service.UserService;
import org.redisson.config.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * description
 *
 * @author zxy
 * create time 2020/3/12 11:11
 */
@Configuration
public class BeanConfig {


    /*  @Bean
      public RedissonClient client(){
          Config config = new Config();
          return Redisson.create(config);
      }*/
    @Bean
    public NameSpace nameSpace() {
        return new DefalutNameSpaceImpl();
    }

    @Bean
    public UserService userService() {
        return new CacheUserServiceImpl();
    }
}
