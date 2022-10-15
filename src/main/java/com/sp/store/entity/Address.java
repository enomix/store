package com.sp.store.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * 收获地址数据的实体类
 * @author sp
 * @date: 2022.10.07 23:51
 */
@Data
public class Address extends BaseEntity implements Serializable {
    private Integer aid;
    private Integer uid;
    private String name;
    private String provinceName;
    private String provinceCode;
    private String cityName;
    private String cityCode;
    private String areaName;
    private String areaCode;
    private String zip;
    private String address;
    private String phone;
    private String tel;
    private String tag;
    private Integer isDefault;
}
