package com.sp.store.service;

import com.sp.store.entity.Order;

/**
 * 处理订单和订单数据的业务层接口
 */
public interface IOrderService {
    /**
     * 创建订单
     * @param aid 收货地址的id
     * @param cids 购物车中勾选了即将要购买的商品id
     * @param uid 当前登录的用户的id
     * @param username 当前登录的用户名
     * @return 成功创建的订单数据
     */
    Order create(Integer aid, Integer[] cids, Integer uid, String username);
}
