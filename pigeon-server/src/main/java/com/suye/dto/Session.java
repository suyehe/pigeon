package com.suye.dto;

import com.google.common.base.MoreObjects;
import com.suye.consts.Protocol;
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
    private long userId;

    private String username;

    private String sessionId;

    private String appId;

    private String serverAddr;

    private Protocol protocol;

    private String token;

    private boolean active;



    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("sessionId", sessionId)
                .add("app", appId)
                .add("serverAddr", serverAddr)
                .add("token", token)
                .add("username", username)
                .add("active", active)
                .toString();
    }
}
