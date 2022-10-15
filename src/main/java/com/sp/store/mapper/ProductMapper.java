package com.sp.store.mapper;

import com.sp.store.entity.Product;

import java.util.List;

/**
 * 处理商品数据的持久层接口
 */
public interface ProductMapper {
    /**
     * 查询热销商品的前4名
     * @return 热销商品的前4名集合
     */
    List<Product> findHotList();
}
