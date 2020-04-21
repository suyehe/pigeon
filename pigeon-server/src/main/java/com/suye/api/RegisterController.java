package com.suye.api;

import com.suye.dto.RegisterSessionReq;
import com.suye.dto.Session;
import com.suye.service.NameSpace;
import com.suye.service.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;


/**
 * description
 *
 * @author zxy
 * create time 2020/4/8 18:18
 */
@RestController
public class RegisterController {

    @Autowired
    private SessionService sessionService;
    @PostMapping("/api/register")
    public Session register(@RequestBody RegisterSessionReq register) {
        return sessionService.generate(register.getUserId(), register.getAppId(), register.getProtocol());
    }
}
