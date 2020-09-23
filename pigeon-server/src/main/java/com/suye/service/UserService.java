package com.suye.service;

import com.suye.dao.LineUser;

/**
 * description
 *
 * @author zxy
 * create time 2020/9/16 16:16
 */
public interface UserService {
    LineUser tokenUser(String token);

    String generateToken(LineUser user);
}
