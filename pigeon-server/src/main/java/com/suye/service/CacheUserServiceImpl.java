package com.suye.service;

import com.google.common.collect.Maps;
import com.suye.dao.LineUser;

import java.util.Map;
import java.util.UUID;

/**
 * description
 *
 * @author zxy
 * create time 2020/9/17 17:17
 */
public class CacheUserServiceImpl implements UserService {
    private static final Map<String,LineUser> USER_TOKEN =Maps.newConcurrentMap();



    @Override
    public LineUser tokenUser(String token) {
        return USER_TOKEN.get(token);
    }
    @Override
    public String generateToken(LineUser user){
        String token = UUID.randomUUID().toString().replace("-", "");
        USER_TOKEN.put(token,user);
        return token;
    }
}
