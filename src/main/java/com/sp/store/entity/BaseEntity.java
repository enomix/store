package com.sp.store.entity;

import lombok.Data;

import java.util.Date;

/**
 * 作为实体类的基类
 * @author sp
 * @date: 2022.09.28 02:15
 */
@Data
public class BaseEntity {
    private String createdUser;
    private Date createdTime;
    private String modifiedUser;
    private Date modifiedTime;


}
