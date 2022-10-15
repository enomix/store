package com.sp.store.service;

import com.sp.store.entity.Address;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * 地址测试
 * @author sp
 * @date: 2022.10.08 02:13
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class AddressServiceTests {
    @Autowired
    private IAddressService addressService;

    @Test
    public void addNewAddress() {
        Address address = new Address();
        address.setName("小红");
        address.setPhone("17788888888");
        addressService.addNewAddress(9, "管理员", address);
    }

    @Test
    public void getByUid() {
        List<Address> addressList = addressService.getByUid(8);
        for (Address address : addressList) {
            System.out.println(address);
        }
    }

    @Test
    public void setDefault() {
        addressService.setDefault(8, 8, "管理员");
    }

    @Test
    public void delete() {
        addressService.delete(4, 9, "test01");
    }
}
