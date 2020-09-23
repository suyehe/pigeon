package com.suye.controller;

import com.suye.dao.LineUser;
import com.suye.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * description
 *
 * @author zxy
 * create time 2020/9/21 14:14
 */
@RestController
@CrossOrigin
public class TokenController {
    @Autowired
    private UserService userService;
    @GetMapping("/token")
    public String token(@RequestParam String user,@RequestParam String group){
        LineUser lineUser = new LineUser();
        lineUser.setUserId(user);
        return userService.generateToken(lineUser);
    }


}
