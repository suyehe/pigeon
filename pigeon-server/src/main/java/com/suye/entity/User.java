package com.suye.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GeneratorType;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * description
 *
 * @author zxy
 * create time 2020/4/23 14:14
 */
@Getter
@Setter
@Table(name = "user")
@Entity
public class User  {

    @Id
    @GeneratedValue(strategy =IDENTITY )
    private int id;
    private String nickname;

    private String username;

    private String password;

    private int appId;



}
