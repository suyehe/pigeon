package com.suye.utils;

import com.google.common.collect.Maps;

import java.util.Map;

/**
 * description
 *
 * @author zxy
 * create time 2020/4/20 15:15
 */

public class SpringContext {
    private static Map<String,Object> beans=Maps.newConcurrentMap();

    public static Object regiest(String name, Object object){
        return beans.put(name,object);
    }

    public static <T> T getBean(String name){
        return (T) beans.get(name);
    }

}
