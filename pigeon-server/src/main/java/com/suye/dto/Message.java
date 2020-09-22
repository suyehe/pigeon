package com.suye.dto;



import lombok.Data;

/**
 * description
 *
 * @author zxy
 * create time 2020/3/13 15:15
 */
@Data
public class Message {
    private Long messageId;

    private String userId;

    private String to;

    private String content;



}
