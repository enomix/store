package com.sp.store.service;

import com.sp.store.entity.Address;

import java.util.List;

/**
 * 收货地址业务层接口
 */
public interface IAddressService {
    /**
     * 新增收货地址
     * @param uid 修改的用户的id
     * @param username 修改的用户的用户名
     * @param address 地址信息
     */
    void addNewAddress(Integer uid, String username, Address address);

    /**
     * 查询某个用户的收货地址列表数据
     * @param uid 用户的id
     * @return 该用户的收货地址列表
     */
    List<Address> getByUid(Integer uid);

    /**
     * 设置默认收货地址
     * @param aid 地址id
     * @param uid 用户id
     * @param username 当前登录的用户名
     */
    void setDefault(Integer aid, Integer uid, String username);

    /**
     * 删除收货地址
     * @param aid 地址id
     * @param uid 用户id
     * @param username 用户名
     */
    void delete(Integer aid, Integer uid, String username);

    /**
     * 根据收货地址数据的id, 查询收货地址详情
     * @param aid 收货地址id
     * @param uid 归属的用户id
     * @return 匹配的收货地址详情
     */
    Address getByAid(Integer aid, Integer uid);
}
