package com.sp.store.service.impl;

import com.sp.store.entity.Address;
import com.sp.store.entity.Order;
import com.sp.store.entity.OrderItem;
import com.sp.store.mapper.OrderMapper;
import com.sp.store.service.IAddressService;
import com.sp.store.service.ICartService;
import com.sp.store.service.IOrderService;
import com.sp.store.service.ex.InsertException;
import com.sp.store.vo.CartVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class OrderServiceImpl implements IOrderService {
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private IAddressService addressService;
    @Autowired
    private ICartService cartService;


    @Override
    public Order create(Integer aid, Integer[] cids, Integer uid, String username) {
        List<CartVO> cartVOList = cartService.getVOByCids(uid, cids);
        //计算商品总价
        long totalPrice = 0;
        for (CartVO cartVO : cartVOList) {
            totalPrice += cartVO.getPrice() * cartVO.getNum();
            System.out.println(cartVO);
        }

        Order order = new Order();
        order.setUid(uid);
        //查询收货地址数据
        Address address = addressService.getByAid(aid, uid);
        order.setRecvName(address.getName());
        order.setRecvPhone(address.getPhone());
        order.setRecvProvince(address.getProvinceName());
        order.setRecvCity(address.getCityName());
        order.setRecvArea(address.getAreaName());
        order.setRecvAddress(address.getAddress());
        order.setTotalPrice(totalPrice);
        order.setStatus(0);
        Date now = new Date();
        order.setOrderTime(now);
        order.setCreatedUser(username);
        order.setCreatedTime(now);
        order.setModifiedUser(username);

        Integer rows = orderMapper.insertOrder(order);
        if (rows != 1) {
            throw new InsertException("插入订单数据时出现错误");
        }

        for (CartVO cartVO : cartVOList) {
            OrderItem orderItem = new OrderItem();
            orderItem.setOid(order.getOid());
            orderItem.setPid(cartVO.getPid());
            orderItem.setTitle(cartVO.getTitle());
            orderItem.setImage(cartVO.getImage());
            orderItem.setPrice(cartVO.getPrice());
            orderItem.setNum(cartVO.getNum());
            orderItem.setCreatedTime(now);
            orderItem.setCreatedUser(username);
            orderItem.setModifiedTime(now);
            orderItem.setModifiedUser(username);
            Integer rows2 = orderMapper.insertOrderItem(orderItem);
            if (rows2 != 1) {
                throw new InsertException("插入订单商品数据时出现错误");
            }
        }
        return order;
    }
}
