package com.suye.dto;

import com.suye.consts.Protocol;
import lombok.Data;

/**
 * description
 *
 * @author zxy
 * create time 2020/4/22 17:17
 */
@Data
public class Server {

    private String addr;

    private Protocol protocol;
}
