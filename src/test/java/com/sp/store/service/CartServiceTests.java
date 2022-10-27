package com.sp.store.service;

import com.sp.store.vo.CartVO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

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

    @Test
    public void findVOByUid() {
        List<CartVO> list = cartService.getVOByUid(1);
        for (CartVO cartVO : list) {
            System.out.println(cartVO);
        }
    }

    @Test
    public void addNum() {
        Integer num = cartService.addNum(4, 1, "管理员");
        System.out.println("num = " + num);
    }

    @Test
    public void getVOByCids() {
        Integer[] cids = {1, 2, 3, 4, 5};
        List<CartVO> cartVOList = cartService.getVOByCids(1, cids);
        for (CartVO cartVO : cartVOList) {
            System.out.println(cartVO);
        }
    }
}
