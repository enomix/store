package com.sp.store.service;

import com.sp.store.entity.District;

import java.util.List;

public interface IDistrictService {
    List<District> getByParent(String parent);

    /**
     * 通过code获取省市区名称
     * @param code 省/市/区的行政代号
     * @return 匹配的省/市/区的名称，如果没有匹配的数据则返回null
     */
    String getNameByCode(String code);
}
