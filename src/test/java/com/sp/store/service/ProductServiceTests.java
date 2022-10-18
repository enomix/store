package com.sp.store.service;


import com.sp.store.entity.Product;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @author sp
 * @date: 2022.10.08 02:13
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class ProductServiceTests {
    @Autowired
    private IProductService productService;

    @Test
    public void findHotList() {
        List<Product> list = productService.findHotList();
        for (Product product : list) {
            System.out.println(product);
        }
    }

    @Test
    public void findById() {
        Product product = productService.findById(10000017);
        System.out.println(product);
    }

}
