package com.suye.entity;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

/**
 * description
 *
 * @author zxy
 * create time 2020/4/23 15:15
 */

public interface RolePermissionRepository extends JpaRepository<RolePermission,Integer> {
    List<RolePermission> queryAllById(Set<Integer> ids);
}
