package com.sp.store.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * 商品数据的实体类
 * @author sp
 * @date: 2022.10.13 22:34
 */
@Data
public class Product extends BaseEntity implements Serializable {
    private Integer id;
    private Integer categoryId;
    private String itemType;
    private String title;
    private String sellPoint;
    private Long price;
    private Integer num;
    private String image;
    private Integer status;
    private Integer priority;
}
