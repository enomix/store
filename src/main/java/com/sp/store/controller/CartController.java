package com.sp.store.controller;

import com.sp.store.service.ICartService;
import com.sp.store.util.JsonResult;
import com.sp.store.vo.CartVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * 购物车控制层
 */
@RestController
@RequestMapping("carts")
public class CartController extends BaseController{
    @Autowired
    private ICartService cartService;

    @RequestMapping("add_to_cart")
    public JsonResult<Void> addToCart(Integer pid, Integer amount, HttpSession session) {
        Integer uid = getUidFromSession(session);
        String username = getUsernameFromSession(session);
        cartService.addToCart(uid, pid, amount, username);
        return new JsonResult<Void>(OK);
    }

    @GetMapping({"", "/"})
    public JsonResult<List<CartVO>> getVOByUid(HttpSession session) {
        Integer uid = getUidFromSession(session);
        List<CartVO> cartVOList = cartService.getVOByUid(uid);
        return new JsonResult<List<CartVO>>(OK, cartVOList);
    }

    @RequestMapping("{cid}/num/add")
    public JsonResult<Integer> addNum(@PathVariable("cid") Integer cid, HttpSession session) {
        Integer uid = getUidFromSession(session);
        String username = getUsernameFromSession(session);
        Integer data = cartService.addNum(cid, uid, username);
        return new JsonResult<Integer>(OK, data);
    }

    @RequestMapping("list")
    public JsonResult<List<CartVO>> getVOByCids(Integer[] cids, HttpSession session) {
        Integer uid = getUidFromSession(session);
        List<CartVO> cartVOList = cartService.getVOByCids(uid, cids);
        return new JsonResult<List<CartVO>>(OK, cartVOList);
    }
}
