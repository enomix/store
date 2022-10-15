package com.sp.store.mapper;

import com.sp.store.entity.District;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;


/**
 * @author sp
 * @date: 2022.09.28 12:47
 */
//@SpringBootTest: 表示标注当前的类是一个测试类, 不会随同项目一块打包
//@RunWith: 表示启动这个单元测试类(单元测试类时不能够运行的), 需要传递一个参数, 必须是 SpringRunner的实例类型
@SpringBootTest
@RunWith(SpringRunner.class)
public class DistrictMapperTests {

    @Autowired
    private DistrictMapper districtMapper;


    @Test
    public void findByParent() {
        List<District> districts = districtMapper.findByParent("110100");
        for (District district : districts) {
            System.out.println(district);
        }
    }

    @Test
    public void queryDistrictByCode() {
        String name = districtMapper.queryDistrictByCode("110101");
        System.out.println(name);
    }
}
