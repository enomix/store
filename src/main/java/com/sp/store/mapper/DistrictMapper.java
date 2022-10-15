package com.sp.store.mapper;

import com.sp.store.entity.District;

import java.util.List;

/**
 * @author sp
 * @date: 2022.10.10 22:09
 */
public interface DistrictMapper {
    /**
     * 根据父代号查询区域信息
     * @param parent 父代号
     * @return 某个父区域下的所有区域列表
     */
    List<District> findByParent(String parent);

    /**
     * 根据code查询当前省市区的名称
     * @param code
     * @return
     */
    String queryDistrictByCode(String code);
}
