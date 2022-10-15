package com.sp.store.service.impl;

import com.sp.store.entity.District;
import com.sp.store.mapper.DistrictMapper;
import com.sp.store.service.IDistrictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author sp
 * @date: 2022.10.10 23:06
 */
@Service
public class DistrictServiceImpl implements IDistrictService {
    @Autowired
    private DistrictMapper districtMapper;

    @Override
    public List<District> getByParent(String parent) {
        List<District> districtList = districtMapper.findByParent(parent);
        //过滤掉无用的属性, 节省流量, 提高传输效率
        for (District district : districtList) {
            district.setId(null);
            district.setParent(null);
        }
        return districtList;
    }

    @Override
    public String getNameByCode(String code) {
        String name = districtMapper.queryDistrictByCode(code);
        return name;
    }
}
