package com.suye.entity;

import lombok.Data;

import javax.persistence.*;


/**
 * description
 *
 * @author zxy
 * create time 2020/4/23 14:14
 */
@Data
@Table(name = "user_role")
@Entity
public class UserRole {
  @Id
  private int id;
  private  int userId;
  private String role;

}
