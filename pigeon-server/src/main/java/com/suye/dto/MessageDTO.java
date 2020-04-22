package com.suye.dto;

import com.alibaba.fastjson.JSON;
import lombok.Data;

/**
 * description
 *
 * @author zxy
 * create time 2020/3/13 15:15
 */
@Data
public class MessageDTO {
    private Long id;
    private String from;
    private Long to;
    private Long sendTime;
    private String msg;
    private Integer type;
    private Integer status;

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
