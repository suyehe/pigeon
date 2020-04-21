package com.suye;

import com.suye.netty.NettyServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * description
 *
 * @author zxy
 * create time 2020/3/10 17:17
 */
@SpringBootApplication
public class Application {

    public static void main(String[] args) throws InterruptedException {
        SpringApplication.run(Application.class);
        NettyServer server = new NettyServer();
        server.start();

    }
}
