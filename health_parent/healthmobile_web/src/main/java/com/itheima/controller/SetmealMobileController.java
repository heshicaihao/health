package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constant.MessageConstant;
import com.itheima.entity.Result;
import com.itheima.pojo.Setmeal;
import com.itheima.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 移动端 - 套餐展示控制层
 * @author wangxin
 * @version 1.0
 */
@RestController
@RequestMapping("/setmeal")
public class SetmealMobileController {

    //服务接口 （提供给 web后台管理系统  、 移动端工程使用）
    @Reference
    private SetmealService setmealService;

    /**
     * 获取套餐列表数据
     */
    @RequestMapping(value = "/getSetmeal")
    public Result getSetmeal(){
        try {
            List<Setmeal> setmealList = setmealService.findAll();
            return new Result(true, MessageConstant.GET_SETMEAL_LIST_SUCCESS,setmealList);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.GET_SETMEAL_LIST_FAIL);
        }
    }

    /**
     * 套餐详情数据展示
     */
    @RequestMapping(value = "/findById")
    public Result findById(Integer id){
        try {
            Setmeal setmeal = setmealService.findById(id);
            return new Result(true, MessageConstant.QUERY_SETMEAL_SUCCESS,setmeal);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_SETMEAL_FAIL);
        }
    }
}
