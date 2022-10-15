package com.sp.store.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @author sp
 * @date: 2022.09.28 02:21
 */
@Data
public class User extends BaseEntity implements Serializable {
    private Integer uid;
    private String username;
    private String password;
    private String salt;
    private String phone;
    private String email;
    private Integer gender;
    private String avatar;
    private Integer isDelete;
}
