package com.suye.dao;

import io.netty.channel.Channel;
import lombok.*;


import java.time.LocalTime;

/**
 * description
 *
 * @author zxy
 * create time 2020/9/22 11:11
 */
@Setter
@Getter
@ToString
@RequiredArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded =true)
public class LineSession {
    @EqualsAndHashCode.Include
    private final String userId;
    @EqualsAndHashCode.Exclude
    private final String group;
    private LocalTime createTime=LocalTime.now();
    private LocalTime lastTime;
    private final Channel channel;


}
