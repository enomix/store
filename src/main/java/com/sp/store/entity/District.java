package com.sp.store.entity;

import lombok.Data;

/**
 * 省市区的数据实体类
 * @author sp
 * @date: 2022.10.10 21:50
 */
@Data
public class District extends BaseEntity{
    private Integer id;
    private String parent;
    private String code;
    private String name;
}
