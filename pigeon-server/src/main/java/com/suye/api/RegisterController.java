package com.suye.api;

import com.suye.dto.RegisterSessionVO;
import com.suye.dto.Session;
import com.suye.service.NameSpace;
import com.suye.service.SessionService;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresGuest;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;


/**
 * description
 *
 * @author zxy
 * create time 2020/4/8 18:18
 */
@RestController
@RequestMapping("/api/session")
public class RegisterController {

    @Autowired
    private SessionService sessionService;
    @PostMapping("/register")
    public Session register(@RequestBody RegisterSessionVO register) {
        return sessionService.generate(register.getUserId(), register.getAppId(), register.getProtocol());
    }

    @GetMapping("/all")
    @RequiresPermissions("client:list")
    public Collection<Session> listAll(){
        return NameSpace.all();
    }

    @GetMapping("/admin")
    public Collection<Session> sessions(){
        return NameSpace.all();
    }




}
