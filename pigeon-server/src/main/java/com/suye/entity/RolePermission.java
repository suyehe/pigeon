package com.suye.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * description
 *
 * @author zxy
 * create time 2020/4/23 14:14
 */
@Data
@Table(name = "role_permission")
@Entity
public class RolePermission {
    @Id
    private int id;

    private String role;

    private String description;

    private int permission;
}
