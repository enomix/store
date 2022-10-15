package com.sp.store.controller;

import com.sp.store.entity.Product;
import com.sp.store.service.IProductService;
import com.sp.store.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author sp
 * @date: 2022.10.13 23:41
 */
@RequestMapping("products")
@RestController
public class ProductController extends BaseController{
    @Autowired
    private IProductService productService;

    @RequestMapping("hot_list")
    public JsonResult<List<Product>> getHotList() {
        List<Product> list = productService.findHotList();
        return new JsonResult<List<Product>>(OK, list);
    }
}
