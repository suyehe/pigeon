package com.suye.entity;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * description
 *
 * @author zxy
 * create time 2020/4/23 15:15
 */
public interface UserRepository extends JpaRepository<User,Integer> {
    User getByUsername(String username);
}
