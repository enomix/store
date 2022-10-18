package com.sp.store.controller;

import com.sp.store.entity.Product;
import com.sp.store.service.IProductService;
import com.sp.store.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
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

    @RequestMapping("{id}/details")
    public JsonResult<Product> getById(@PathVariable("id") Integer id) {
        //调用业务对象执行获取数据
        Product data = productService.findById(id);
        //返回成功 和 数据
        return new JsonResult<Product>(OK, data);
    }
}
