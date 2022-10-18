package com.sp.store.service.impl;

import com.sp.store.entity.Product;
import com.sp.store.mapper.ProductMapper;
import com.sp.store.service.IProductService;
import com.sp.store.service.ex.ProductNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author sp
 * @date: 2022.10.13 23:23
 */
@Service
public class ProductServiceImpl implements IProductService {
    @Autowired
    private ProductMapper productMapper;

    @Override
    public List<Product> findHotList() {
        List<Product> list = productMapper.findHotList();
        for (Product product : list) {
            product.setPriority(null);
            product.setCreatedTime(null);
            product.setCreatedUser(null);
            product.setModifiedTime(null);
            product.setModifiedUser(null);
        }
        return list;
    }

    @Override
    public Product findById(Integer id) {
        //根据参数id调用私有方法执行查询, 获取商品数据
        Product product = productMapper.findById(id);
        //判断查询结果是否为null
        if (product == null) {
            throw new ProductNotFoundException("商品数据不存在");
        }
        //将查询结果中的属性设置为null
        product.setPriority(null);
        product.setCreatedUser(null);
        product.setCreatedTime(null);
        product.setModifiedUser(null);
        product.setModifiedTime(null);
        //返回结果
        return product;
    }
}
