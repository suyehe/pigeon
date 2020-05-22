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
@Table(name = "permission")
@Entity
public class Permission {
    @Id
    private int id;

    private String permission;

    private String description;


}
