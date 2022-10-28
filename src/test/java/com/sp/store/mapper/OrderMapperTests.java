package com.sp.store.mapper;

import com.sp.store.entity.Order;
import com.sp.store.entity.OrderItem;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderMapperTests {
    @Autowired
    private OrderMapper orderMapper;

    @Test
    public void insertOrder() {
        Order order = new Order();
        order.setUid(1);
        order.setRecvName("小李");
        Integer rows = orderMapper.insertOrder(order);
        System.out.println("受影响的行数: " + rows);
    }

    @Test
    public void insertOrderItem() {
        OrderItem orderItem = new OrderItem();
        orderItem.setOid(1);
        orderItem.setPid(2);
        orderItem.setTitle("铅笔");
        Integer rows = orderMapper.insertOrderItem(orderItem);
        System.out.println("受影响的行数: " + rows);
    }
}
