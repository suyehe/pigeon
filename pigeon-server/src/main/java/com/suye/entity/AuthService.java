package com.suye.entity;

import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.subject.PrincipalCollection;
import org.aspectj.bridge.MessageWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * description
 *
 * @author zxy
 * create time 2020/4/23 16:16
 */
@Service
public class AuthService {

    @Autowired
    private RolePermissionRepository rolePermissionRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Autowired
    private PermissionRepository permissionRepository;

    @Autowired
    private UserRepository userRepository;



    public SimpleAuthorizationInfo authorizationInfo(UserInfo userInfo) {
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        List<UserRole> userRoles = userRoleRepository.queryAllByUserId(userInfo.getUserId());
        Set<String> roles = userRoles.stream().map(UserRole::getRole).collect(Collectors.toSet());
        List<RolePermission> rolePermissions = rolePermissionRepository.queryAllByRole(roles);
        Set<String> permissions = rolePermissions.stream().map(RolePermission::getPermission).collect(Collectors.toSet());
        info.setRoles(roles);
        info.setStringPermissions(permissions);
        return info;
    }

    public UserInfo userInfo(String userName,String password){
        User user = userRepository.getByUsername(userName);
        if (Objects.nonNull(user)&&Objects.equals(user.getPassword(),password)){
            return new UserInfo(user.getId(),userName);
        }
        return new UserInfo();

    }


}
