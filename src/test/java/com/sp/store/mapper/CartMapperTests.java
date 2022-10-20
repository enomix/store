package com.sp.store.mapper;

import com.sp.store.entity.Address;
import com.sp.store.entity.Cart;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;



//@SpringBootTest: 表示标注当前的类是一个测试类, 不会随同项目一块打包
//@RunWith: 表示启动这个单元测试类(单元测试类时不能够运行的), 需要传递一个参数, 必须是 SpringRunner的实例类型
@SpringBootTest
@RunWith(SpringRunner.class)
public class CartMapperTests {

    @Autowired
    private CartMapper cartMapper;

    @Test
    public void insert() {
        Cart cart = new Cart();
        cart.setUid(1);
        cart.setPid(2);
        cart.setNum(3);
        cart.setPrice(4L);
        cart.setCreatedTime(new Date());
        cart.setCreatedUser("管理员1");
        cart.setModifiedTime(new Date());
        cart.setModifiedUser("管理员1");
        Integer rows = cartMapper.insert(cart);
        System.out.println(rows);
    }

    @Test
    public void updateNumByCid() {
        Cart cart = new Cart();
        Integer rows = cartMapper.updateNumByCid(2, 222, "管理员update", new Date());
        System.out.println(rows);
    }

    @Test
    public void findByUidAndPid() {
        Cart cart = cartMapper.findByUidAndPid(1, 2);
        System.out.println(cart);
    }
}
