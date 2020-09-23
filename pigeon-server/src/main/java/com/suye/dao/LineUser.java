package com.suye.dao;

import io.netty.channel.Channel;
import lombok.Getter;
import lombok.Setter;



/**
 * description
 *
 * @author zxy
 * create time 2020/9/22 17:17
 */
@Setter
@Getter
public class LineUser {
    private String userId;
    private String group;

   public LineSession toSession(Channel channel){
       return new LineSession(userId,group,channel);
   }
}
