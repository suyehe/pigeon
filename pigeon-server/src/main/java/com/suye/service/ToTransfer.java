package com.suye.service;

import com.suye.dao.LineSession;

import java.util.List;

/**
 * description
 *
 * @author zxy
 * create time 2020/9/21 17:17
 */
public interface ToTransfer {

    List<LineSession> userId(String to);
}
