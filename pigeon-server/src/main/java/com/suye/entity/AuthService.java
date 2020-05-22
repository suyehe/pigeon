package com.suye.entity;

import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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
        Set<Integer> rolePermissionIds = userRoles.stream().map(UserRole::getRolePermission).collect(Collectors.toSet());
        List<RolePermission> rolePermissions = rolePermissionRepository.queryAllById(rolePermissionIds);
        Set<Integer> permissionIds = rolePermissions.stream().map(RolePermission::getPermission).collect(Collectors.toSet());
        List<Permission> permissions = permissionRepository.queryById(permissionIds);
        info.setRoles(rolePermissions.stream().map(RolePermission::getRole).collect(Collectors.toSet()));
        info.setStringPermissions(permissions.stream().map(Permission::getPermission).collect(Collectors.toSet()));
        return info;
    }

    public UserInfo userInfo(String userName,String pssword){
        User user = userRepository.getByUsername(userName);
        UserInfo info = new UserInfo();
        info.setUserId(user.getId());
        user.setUsername(userName);
        return info;

    }


}
