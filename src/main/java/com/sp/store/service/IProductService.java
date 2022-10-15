package com.sp.store.service;

import com.sp.store.entity.Product;

import java.util.List;

/**
 * 商品接口
 */
public interface IProductService {
    /**
     * 查询热销榜的第一到第四名
     * @return 热销榜单前四名的集合
     */
    List<Product> findHotList();
}
