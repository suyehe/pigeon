package com.suye.controller;

import com.suye.dao.LineSession;
import com.suye.service.NameSpace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


import java.util.Set;

/**
 * description
 *
 * @author zxy
 * create time 2020/9/21 14:14
 */
@RestController("/online")
@CrossOrigin
public class OnlineController {
    @Autowired
    private NameSpace nameSpace;
    @GetMapping
    public Set<LineSession> online(){
        return nameSpace.onlie();
    }


}
