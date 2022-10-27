package com.sp.store.service;

import com.sp.store.vo.CartVO;

import java.util.List;

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

    /**
     * 查询某个用户的购物车数据
     * @param uid 用户id
     * @return 该用户的购物车数据列表
     */
    List<CartVO> getVOByUid(Integer uid);

    /**
     * 将购物车中的某商品的数量加1
     * @param cid 购物车数量的id
     * @param uid 当前登录的用户的id
     * @param username 当前登录的用户名
     * @return 增加成功后新的数量
     */
    Integer addNum(Integer cid, Integer uid, String username);

    /**
     * 根据购物车数据id数组查询详情的列表
     * @param uid 当前登录的用户的id
     * @param cids 购物车数据id数组
     * @return 匹配的购物车数据详情的列表
     */
    List<CartVO> getVOByCids(Integer uid, Integer[] cids);
}
