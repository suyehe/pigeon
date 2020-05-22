package com.suye.entity;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

/**
 * description
 *
 * @author zxy
 * create time 2020/4/23 17:17
 */
public interface PermissionRepository extends JpaRepository<Permission,Integer> {

    List<Permission> queryById(Set<Integer> ids);
}
