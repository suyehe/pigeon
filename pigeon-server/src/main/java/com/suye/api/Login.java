package com.suye.api;

import com.suye.dto.Response;
import com.suye.entity.User;
import com.suye.entity.UserRepository;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * description
 *
 * @author zxy
 * create time 2020/4/23 18:18
 */
@RestController
@RequestMapping("/api/user")
public class Login {
    @Autowired
    private UserRepository userRepository;
  @RequestMapping("/login")
  public Response add(String username, String password){
      Subject subject = SecurityUtils.getSubject();
      subject.login(new UsernamePasswordToken(username, password));
      return Response.success();
  }


}
