package com.sp.store.mapper;

import com.sp.store.entity.Address;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;


/**
 * @author sp
 * @date: 2022.09.28 12:47
 */
//@SpringBootTest: 表示标注当前的类是一个测试类, 不会随同项目一块打包
//@RunWith: 表示启动这个单元测试类(单元测试类时不能够运行的), 需要传递一个参数, 必须是 SpringRunner的实例类型
@SpringBootTest
@RunWith(SpringRunner.class)
public class AddressMapperTests {

    @Autowired
    private AddressMapper addressMapper;

    @Test
    public void insert() {
        Address address = new Address();
        address.setUid(9);
        address.setName("admin");
        address.setPhone("17788888888");
        address.setAddress("学校");
        Integer rows = addressMapper.insert(address);
        System.out.println(rows);
    }

    @Test
    public void countByUid() {
        Integer num = addressMapper.countByUid(9);
        System.out.println(num);
    }

    @Test
    public void findByUid() {
        List<Address> addressList = addressMapper.findByUid(8);
        for (Address address : addressList) {
            System.out.println(address);
        }
    }

    @Test
    public void findByAid() {
        Address address = addressMapper.findByAid(9);
        System.out.println(address);
    }

    @Test
    public void updateNonDefault() {
        Integer integer = addressMapper.updateNonDefault(8);
        System.out.println(integer);
    }

    @Test
    public void updateDefaultByAid() {
        Integer integer = addressMapper.updateDefaultByAid(6, "管理员", new Date());
        System.out.println(integer);
    }

    @Test
    public void deleteByAid() {
        Integer row = addressMapper.deleteByAid(1);
        System.out.println("受影响的行数: " + row);
    }

    @Test
    public void findLastModified() {
        Address address = addressMapper.findLastModified(9);
        System.out.println(address);
    }
}
