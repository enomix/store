package com.sp.store.service;

import com.sp.store.entity.Order;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderServiceTests {
    @Autowired
    private IOrderService orderService;

    @Test
    public void create() {
        Integer aid = 1;
        Integer[] cids = {1, 2, 3, 4};
        Integer uid = 1;
        String username = "管理员";
        Order order = orderService.create(aid, cids, uid, username);
        System.out.println(order);
    }
}
