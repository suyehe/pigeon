package com.suye.dto;

import com.suye.consts.Protocol;
import lombok.Getter;
import lombok.Setter;

/**
 * description
 *
 * @author zxy
 * create time 2020/4/8 18:18
 */
@Setter
@Getter
public class RegisterSessionReq {

    private long userId;

    private String appId;

    private Protocol protocol;

}
