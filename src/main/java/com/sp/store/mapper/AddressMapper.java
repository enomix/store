package com.sp.store.mapper;

import com.sp.store.entity.Address;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface AddressMapper {

    /**
     * 插入用户的收货地址数据
     * @param address 收获地址数据
     * @return 受影响的行数
     */
    Integer insert(Address address);

    /**
     * 根据用户的id统计收货地址数量
     * @param uid 用户的id
     * @return 当前用户的收货地址总数
     */
    Integer countByUid(Integer uid);

    /**
     * 根据用户id查找其所有的地址
     * @param uid 用户id
     * @return 该用户的地址列表
     */
    List<Address> findByUid(Integer uid);

    /**
     * 根据aid查询收货地址
     * @param aid 收货地址id
     * @return 收货地址数据, 如果没有找到则返回null值
     */
    Address findByAid(Integer aid);

    /**
     * 根据用户的uid值来将用户的收货地址设置为非默认
     * @param uid 用户的id
     * @return 受影响的行数
     */
    Integer updateNonDefault(Integer uid);

    /**
     * 根据用户的uid值来将用户的收货地址设置为默认
     * @param aid 用户的id
     * @param modifiedUser 修改者的名字
     * @param modifiedTime 修改时间
     * @return 受影响的行数
     */
    Integer updateDefaultByAid(@Param("aid") Integer aid, @Param("modifiedUser") String modifiedUser, @Param("modifiedTime") Date modifiedTime);

    /**
     * 根据地址id删除地址
     * @param aid 地址id
     * @return 受影响的行数
     */
    Integer deleteByAid(Integer aid);

    /**
     * 查询某个用户最后修改的地址
     * @param uid 用户id
     * @return 该用户最后修改的地址, 没有记录则返回null
     */
    Address findLastModified(Integer uid);
}
