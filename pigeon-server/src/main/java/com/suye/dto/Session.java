package com.suye.dto;


import lombok.Data;

import java.io.Serializable;

/**
 * description
 *
 * @author zxy
 * create time 2020/4/8 18:18
 */
@Data
public class Session implements Serializable {
    private Long userId;

    private Long sessionId;

    private String appId;

    private Server server;


    private String token;

    private boolean active;




}
