package com.suye.config;

import com.suye.entity.AuthService;
import com.suye.entity.UserInfo;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * description
 *
 * @author zxy
 * create time 2020/4/23 11:11
 */
@Configuration
public class ShrioConfig {
    @Autowired
    private AuthService authService;


    @Bean
    public Realm realm() {
        return new AuthorizingRealm() {
            @Override
            protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
                UsernamePasswordToken upToken = (UsernamePasswordToken) token;
                String username = upToken.getUsername();
                String password = new String(upToken.getPassword());

                if (StringUtils.isEmpty(username)) {
                    throw new AccountException("用户名不能为空");
                }
                if (StringUtils.isEmpty(password)) {
                    throw new AccountException("密码不能为空");
                }
                UserInfo userInfo = authService.userInfo(username,password);
                return new SimpleAuthenticationInfo(userInfo, password, getName());

            }

            @Override
            protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
                return authService.authorizationInfo((UserInfo) principals);
            }
        };

    }


    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<String, String>();
        filterChainDefinitionMap.put("/api/session/*", "authc");
        filterChainDefinitionMap.put("/api/login/*","anon");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return shiroFilterFactoryBean;
    }

}
