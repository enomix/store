package com.sp.store.controller;

import com.sp.store.entity.District;
import com.sp.store.service.IDistrictService;
import com.sp.store.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.sp.store.controller.BaseController.OK;

/**
 * @author sp
 * @date: 2022.10.10 23:36
 */
@RequestMapping("districts")
@RestController
public class DistrictController extends BaseController{
    @Autowired
    private IDistrictService districtService;

    //districts开头的请求都被拦截到这个方法
    @RequestMapping({"/", ""})
    public JsonResult<List<District>> getByParent(String parent) {
        List<District> districtList = districtService.getByParent(parent);
        return new JsonResult<>(OK, districtList);
    }


}
