package com.sp.store.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * 购物车业务层测试
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class CartServiceTests {
    @Autowired
    private ICartService cartService;

    @Test
    public void addToCart() {
        //cartService.addToCart(1, 1, 9, "管理员");//插入测试
        cartService.addToCart(1, 10000001, 9, "管理员");//添加测试
    }
}
