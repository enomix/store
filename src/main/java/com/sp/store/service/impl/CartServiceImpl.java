package com.sp.store.service.impl;

import com.sp.store.entity.Cart;
import com.sp.store.entity.Product;
import com.sp.store.mapper.CartMapper;
import com.sp.store.mapper.ProductMapper;
import com.sp.store.service.ICartService;
import com.sp.store.service.ex.AccessDeniedException;
import com.sp.store.service.ex.CartNotFoundException;
import com.sp.store.service.ex.InsertException;
import com.sp.store.service.ex.UpdateException;
import com.sp.store.vo.CartVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * 处理购物车数据的实现类
 */
@Service
public class CartServiceImpl implements ICartService {
    @Autowired
    private CartMapper cartMapper;
    @Autowired
    private ProductMapper productMapper;

    @Override
    public void addToCart(Integer uid, Integer pid, Integer amount, String username) {
        // 根据参数pid和uid查询购物车中的数据
        Cart result = cartMapper.findByUidAndPid(uid, pid);
        // 判断查询结果是否为null
        if (result == null) {
            Cart cart = new Cart();
            cart.setUid(uid);
            cart.setPid(pid);
            cart.setNum(amount);
            Product product = productMapper.findById(pid);
            Long price = product.getPrice();
            cart.setPrice(price);
            cart.setModifiedUser(username);
            cart.setModifiedTime(new Date());
            cart.setCreatedUser(username);
            cart.setCreatedTime(new Date());
            Integer rows1 = cartMapper.insert(cart);
            if (rows1 != 1) {
                throw new InsertException("插入商品数据产生异常");
            }
        } else {
            Integer cid = result.getCid();
            Integer num = result.getNum();
            Integer newNum = num + amount;//购物车新的数量 = 原数量 + 增加的数量
            Integer rows2 = cartMapper.updateNumByCid(cid, newNum, username, new Date());//更新
            if (rows2 != 1) {
                throw new UpdateException("更新购物车数据产生异常");
            }
        }
        // 是：表示该用户并未将该商品添加到购物车
        // -- 创建Cart对象
        // -- 封装数据：uid,pid,amount
        // -- 调用productService.findById(pid)查询商品数据，得到商品价格
        // -- 封装数据：price
        // -- 封装数据：4个日志
        // -- 调用insert(cart)执行将数据插入到数据表中
        // 否：表示该用户的购物车中已有该商品
        // -- 从查询结果中获取购物车数据的id
        // -- 从查询结果中取出原数量，与参数amount相加，得到新的数量
        // -- 执行更新数量
    }

    @Override
    public List<CartVO> getVOByUid(Integer uid) {
        List<CartVO> cartVOList = cartMapper.findVOByUid(uid);
        return cartVOList;
    }

    @Override
    public Integer addNum(Integer cid, Integer uid, String username) {
        //根据参数cid查询购物车数据
        Cart result = cartMapper.findByCid(cid);
        if (result == null) {
            throw new CartNotFoundException("尝试访问的购物车数据不存在");
        }
        //判断查询结果中的uid与参数uid是否不一致
        if (!result.getUid().equals(uid)) {
            throw new AccessDeniedException("非法访问");
        }
        //检查商品的数量是否大于多少
        Integer num = result.getNum() + 1;
        //创建当前时间对象, 作为modifiedTime
        Date now = new Date();
        Integer rows = cartMapper.updateNumByCid(cid, num, username, now);
        if (rows != 1) {
            throw new InsertException("修改商品数量时出现未知错误");
        }

        //返回新的数量
        return num;
    }

    @Override
    public List<CartVO> getVOByCids(Integer uid, Integer[] cids) {
        List<CartVO> cartVOList = cartMapper.findVOByCids(cids);
        Iterator<CartVO> iterator = cartVOList.iterator();
        while (iterator.hasNext()) {
            CartVO cartVO = iterator.next();
            if (!cartVO.getUid().equals(uid)) {
                iterator.remove();
            }
        }
        return cartVOList;
    }
}
