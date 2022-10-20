package com.sp.store.service;

/**
 * 处理商品数据的业务接口
 */
public interface ICartService {
    /**
     * 将商品添加到购物车
     * @param uid 当前登录的用户id
     * @param pid 商品id
     * @param amount 增加的数量
     * @param username 当前登录的用户名
     */
    void addToCart(Integer uid, Integer pid, Integer amount, String username);
}
