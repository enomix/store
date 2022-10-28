package com.sp.store.service.impl;

import com.sp.store.entity.Address;
import com.sp.store.mapper.AddressMapper;
import com.sp.store.service.IAddressService;
import com.sp.store.service.IDistrictService;
import com.sp.store.service.ex.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 收获地址的实现类
 * @author sp
 * @date: 2022.10.08 01:34
 */
@Service
public class AddressServiceImpl implements IAddressService {
    @Autowired
    private AddressMapper addressMapper;

    //添加用户的收货地址业务层依赖于IDistrictService的业务接口
    @Autowired
    private IDistrictService districtService;

    @Value("${user.address.max-count}")
    private Integer maxCount;

    @Override
    public void addNewAddress(Integer uid, String username, Address address) {
        Integer count = addressMapper.countByUid(uid);
        if (count > maxCount) {
            throw new AddressCountLimitException("收货地址超出上限");
        }

        //对address对象中的数据进行补全: 省市区
        String provinceName = districtService.getNameByCode(address.getProvinceCode());
        String cityCode = districtService.getNameByCode(address.getCityCode());
        String areaName = districtService.getNameByCode(address.getAreaCode());
        address.setProvinceName(provinceName);
        address.setCityName(cityCode);
        address.setAreaName(areaName);

        //三元运算符
        Integer isDefault = count == 0 ? 1 : 0;//1表示默认, 0表示不是默认
        address.setUid(uid);
        address.setIsDefault(isDefault);
        Date date = new Date();
        address.setCreatedUser(username);
        address.setCreatedTime(date);
        address.setModifiedUser(username);
        address.setModifiedTime(date);
        System.out.println(address);
        Integer rows = addressMapper.insert(address);
        if (rows != 1) {
            throw new InsertException("插入收货地址时出现异常");
        }
    }

    @Override
    public List<Address> getByUid(Integer uid) {
        List<Address> addressList = addressMapper.findByUid(uid);
        for (Address address : addressList) {
            address.setUid(null);
            address.setProvinceCode(null);
            address.setCityCode(null);
            address.setAreaCode(null);
            address.setCreatedUser(null);
            address.setCreatedTime(null);
            address.setModifiedUser(null);
            address.setModifiedTime(null);
        }
        return addressList;
    }

    @Override
    public void setDefault(Integer aid, Integer uid, String username) {
        //1. 查询这条地址数据是否存在
        Address result = addressMapper.findByAid(aid);
        if (result == null) {
            throw new AddressNotFoundException("没有这条地址数据");
        }
        //2. 判断这条数据是不是当前用户的数据
        if (!result.getUid().equals(uid)) {
            throw new AccessDeniedException("不能修改其他人的数据");
        }
        //3. 将该用户的所有收货地址修改为非默认
        Integer row1 = addressMapper.updateNonDefault(uid);
        if (!(row1 > 0)) {
            throw new UpdateException("设置默认地址时发生未知错误1");
        }
        //4. 将指定的地址设置为默认地址
        Integer row2 = addressMapper.updateDefaultByAid(aid, username, new Date());
        if (!(row2 > 0)) {
            throw new UpdateException("设置默认地址时发生未知错误2");
        }
    }

    @Override
    public void delete(Integer aid, Integer uid, String username) {
        //1. 删除前, 先检查该条数据是否存在, 数据归属是否正确
        Address result = addressMapper.findByAid(aid);
        if (result == null) {
            throw new AddressNotFoundException("没有找到这条地址");
        }
        if (!result.getUid().equals(uid)) {
            throw new AccessDeniedException("不能修改其他人的地址");
        }

        //2. 删除地址
        Integer row1 = addressMapper.deleteByAid(aid);
        if (row1 != 1) {
            throw new DeleteException("删除地址时出现未知错误");
        }
        //3. 如果删除的是默认地址, 将最后修改的地址给设置为默认地址
        //查询结果中的isDefault是否为0, 不是默认就return
        if (!result.getIsDefault().equals(0)) {
            return;
        }
        //该用户目前还有多少收货地址, 没有就return
        if (addressMapper.countByUid(uid) == 0) {

        }

        //找出最后修改的地址
        Address lastModified = addressMapper.findLastModified(uid);
        Integer lastModifiedAid = lastModified.getAid();
        Integer integer = addressMapper.updateDefaultByAid(lastModifiedAid, username, new Date());
        if (integer != 1) {
            throw new UpdateException("更新默认收货地址出现错误");
        }
    }

    @Override
    public Address getByAid(Integer aid, Integer uid) {
        Address address = addressMapper.findByAid(aid);
        if (address == null) {
            throw new AddressNotFoundException("访问的收货地址数据不存在");
        }
        if (!address.getUid().equals(uid)) {
            throw new AccessDeniedException("非法访问");
        }

        address.setProvinceCode(null);
        address.setCityCode(null);
        address.setAreaCode(null);
        address.setCreatedUser(null);
        address.setCreatedTime(null);
        address.setModifiedUser(null);
        address.setModifiedTime(null);

        return address;
    }
}
