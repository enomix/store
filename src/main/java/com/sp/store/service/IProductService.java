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

    /**
     * 根据商品id查询商品详情
     * @param id 商品id
     * @return 匹配的商品详情，如果没有匹配的数据则返回null
     */
    Product findById(Integer id);
}
