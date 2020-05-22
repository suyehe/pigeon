package com.suye.entity;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * description
 *
 * @author zxy
 * create time 2020/4/23 17:17
 */
public interface UserRoleRepository extends JpaRepository<UserRole,Integer> {


    List<UserRole> queryAllByUserId(Integer userIds);
}
