package com.sp.store.service;

import com.sp.store.entity.Address;
import com.sp.store.entity.District;
import com.sp.store.mapper.DistrictMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * 区域测试
 * @author sp
 * @date: 2022.10.08 02:13
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class DistrictServiceTests {
    @Autowired
    private IDistrictService districtService;

    @Test
    public void getByParent() {
        List<District> districtList = districtService.getByParent("86");
        for (District district : districtList) {
            System.out.println(district);
        }
    }

    @Test
    public void getNameByCode() {
        String name = districtService.getNameByCode("110101");
        System.out.println(name);
    }

}
